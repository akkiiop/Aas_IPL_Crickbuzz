import { useEffect, useState } from "react";

import {
    getAllPlayers,
    getPlayerById,
    deletePlayer
} from "../services/playerService";

import PlayerForm from "../components/PlayerForm";


function Players() {

    // =====================================================
    // STATE
    // =====================================================

    const [players, setPlayers] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [playerToEdit, setPlayerToEdit] = useState(null);


    // =====================================================
    // PAGINATION STATE
    // =====================================================

    const [currentPage, setCurrentPage] = useState(0);

    const [totalPages, setTotalPages] = useState(0);

    const [totalPlayers, setTotalPlayers] = useState(0);


    // =====================================================
    // LOAD PLAYERS
    // =====================================================

    const loadPlayers = async (page = 0) => {

        try {

            setLoading(true);

            setError("");


            const response = await getAllPlayers(page);

            console.log("Players API response:", response.data);


            const data = response.data;


            // -------------------------------------------------
            // SAFETY CHECK
            // -------------------------------------------------

            if (!data) {

                throw new Error("Empty response from server.");

            }


            // Backend returns:
            //
            // {
            //   content: [...],
            //   number: 0,
            //   totalPages: 6,
            //   totalElements: 102
            // }


            setPlayers(

                Array.isArray(data.content)

                    ? data.content

                    : []

            );


            setCurrentPage(

                typeof data.number === "number"

                    ? data.number

                    : page

            );


            setTotalPages(

                typeof data.totalPages === "number"

                    ? data.totalPages

                    : 0

            );


            setTotalPlayers(

                typeof data.totalElements === "number"

                    ? data.totalElements

                    : 0

            );

        }

        catch (error) {

            console.error(

                "Load players error:",

                error

            );


            setPlayers([]);

            setTotalPages(0);

            setTotalPlayers(0);

            setError(

                "Failed to load players."

            );

        }

        finally {

            setLoading(false);

        }

    };


    // =====================================================
    // INITIAL LOAD
    // =====================================================

	useEffect(() => {

	    const loadInitialPlayers = async () => {

	        await loadPlayers(0);

	    };

	    loadInitialPlayers();

	}, []);


    // =====================================================
    // EDIT PLAYER
    // =====================================================

    const handleEdit = async (id) => {

        try {

            setError("");


            const response =

                await getPlayerById(id);


            console.log(

                "Player to edit:",

                response.data

            );


            setPlayerToEdit(

                response.data

            );


            window.scrollTo({

                top: 0,

                behavior: "smooth"

            });

        }

        catch (error) {

            console.error(

                "Load player error:",

                error

            );


            setError(

                "Failed to load player."

            );

        }

    };


    // =====================================================
    // DELETE PLAYER
    // =====================================================

    const handleDelete = async (id) => {

        const confirmed = window.confirm(

            "Are you sure you want to delete this player?"

        );


        if (!confirmed) {

            return;

        }


        try {

            setError("");


            await deletePlayer(id);


            // -------------------------------------------------
            // If deleting the only player on the current page,
            // and we are not on page 1, go to previous page.
            // -------------------------------------------------

            if (

                players.length === 1 &&

                currentPage > 0

            ) {

                await loadPlayers(

                    currentPage - 1

                );

            }

            else {

                await loadPlayers(

                    currentPage

                );

            }

        }

        catch (error) {

            console.error(

                "Delete player error:",

                error

            );


            setError(

                "Failed to delete player."

            );

        }

    };


    // =====================================================
    // PAGE CHANGE
    // =====================================================

    const handlePageChange = (page) => {

        if (loading) {

            return;

        }


        if (page < 0) {

            return;

        }


        if (page >= totalPages) {

            return;

        }


        if (page === currentPage) {

            return;

        }


        loadPlayers(page);


        window.scrollTo({

            top: 0,

            behavior: "smooth"

        });

    };


    // =====================================================
    // PREVIOUS PAGE
    // =====================================================

    const handlePreviousPage = () => {

        handlePageChange(

            currentPage - 1

        );

    };


    // =====================================================
    // NEXT PAGE
    // =====================================================

    const handleNextPage = () => {

        handlePageChange(

            currentPage + 1

        );

    };


    // =====================================================
    // LOADING SCREEN
    // =====================================================

    if (

        loading &&

        players.length === 0

    ) {

        return (

            <div>

                <h2>

                    Loading players...

                </h2>

            </div>

        );

    }


    // =====================================================
    // FATAL ERROR
    // =====================================================

    if (

        error &&

        players.length === 0

    ) {

        return (

            <div>

                <h2>

                    {error}

                </h2>


                <button

                    onClick={() =>

                        loadPlayers(

                            currentPage

                        )

                    }

                >

                    Try Again

                </button>

            </div>

        );

    }


    // =====================================================
    // MAIN UI
    // =====================================================

    return (

        <div>

            <h1>

                IPL Players

            </h1>


            {/* =================================================
                PLAYER FORM
            ================================================== */}

            <PlayerForm

                playerToEdit={

                    playerToEdit

                }


                onPlayerSaved={async () => {

                    await loadPlayers(

                        currentPage

                    );

                }}


                onEditComplete={() => {

                    setPlayerToEdit(

                        null

                    );

                }}

            />


            <hr />


            {/* =================================================
                PLAYER COUNT
            ================================================== */}

            <p>

                <strong>

                    Total Players:

                </strong>{" "}

                {totalPlayers}

            </p>


            {/* =================================================
                PAGE INFORMATION
            ================================================== */}

            {totalPages > 0 && (

                <p>

                    Page{" "}

                    <strong>

                        {currentPage + 1}

                    </strong>{" "}

                    of{" "}

                    <strong>

                        {totalPages}

                    </strong>

                </p>

            )}


            {/* =================================================
                REFRESH
            ================================================== */}

            <button

                onClick={() =>

                    loadPlayers(

                        currentPage

                    )

                }

                disabled={loading}

            >

                {loading

                    ? "Refreshing..."

                    : "Refresh Players"}

            </button>


            <hr />


            {/* =================================================
                ERROR
            ================================================== */}

            {error && (

                <p>

                    {error}

                </p>

            )}


            {/* =================================================
                PLAYER LIST
            ================================================== */}

            {players.length === 0 ? (

                <p>

                    No players found.

                </p>

            ) : (

                players.map(

                    (player) => (

                        <div

                            key={

                                player.playerId

                            }

                        >

                            <h3>

                                {player.playerName}

                            </h3>


                            <p>

                                Jersey Number:{" "}

                                {player.jerseyNumber}

                            </p>


                            <p>

                                Team:{" "}

                                {player.teamName}

                            </p>


                            <p>

                                Runs:{" "}

                                {player.runs}

                            </p>


                            <p>

                                Wickets:{" "}

                                {player.wickets}

                            </p>


                            <p>

                                Specialization:{" "}

                                {player.specialization}

                            </p>


                            {/* ---------------------------------
                                EDIT
                            ---------------------------------- */}

                            <button

                                onClick={() =>

                                    handleEdit(

                                        player.playerId

                                    )

                                }

                                disabled={loading}

                            >

                                Edit

                            </button>


                            {" "}


                            {/* ---------------------------------
                                DELETE
                            ---------------------------------- */}

                            <button

                                onClick={() =>

                                    handleDelete(

                                        player.playerId

                                    )

                                }

                                disabled={loading}

                            >

                                Delete

                            </button>


                            <hr />

                        </div>

                    )

                )

            )}


            {/* =================================================
                PAGINATION
            ================================================== */}

            {totalPages > 1 && (

                <div>

                    <hr />


                    <h3>

                        Pagination

                    </h3>


                    {/* -----------------------------------------
                        PREVIOUS
                    ------------------------------------------ */}

                    <button

                        onClick={

                            handlePreviousPage

                        }

                        disabled={

                            currentPage === 0 ||

                            loading

                        }

                    >

                        Previous

                    </button>


                    {" "}


                    {/* -----------------------------------------
                        PAGE NUMBERS
                    ------------------------------------------ */}

                    {Array.from(

                        {

                            length: totalPages

                        },

                        (_, index) => index

                    ).map(

                        (page) => (

                            <button

                                key={page}

                                onClick={() =>

                                    handlePageChange(

                                        page

                                    )

                                }

                                disabled={loading}

                                style={{

                                    fontWeight:

                                        currentPage === page

                                            ? "bold"

                                            : "normal",

                                    margin:

                                        "0 3px"

                                }}

                            >

                                {page + 1}

                            </button>

                        )

                    )}


                    {" "}


                    {/* -----------------------------------------
                        NEXT
                    ------------------------------------------ */}

                    <button

                        onClick={

                            handleNextPage

                        }

                        disabled={

                            currentPage ===

                                totalPages - 1 ||

                            loading

                        }

                    >

                        Next

                    </button>


                    <p>

                        Showing page{" "}

                        <strong>

                            {currentPage + 1}

                        </strong>{" "}

                        of{" "}

                        <strong>

                            {totalPages}

                        </strong>

                    </p>

                </div>

            )}

        </div>

    );

}


export default Players;