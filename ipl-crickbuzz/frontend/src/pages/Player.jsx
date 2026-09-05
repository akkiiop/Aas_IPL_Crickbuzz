import React, { useEffect, useState } from "react";

import {
    getAllPlayers,
    getPlayerById,
    createPlayer,
    updatePlayer,
    deletePlayer,
    searchPlayers,
    getPlayersByTeam
} from "../services/playerService";

import PlayerForm from "../components/PlayerForm";

const Player = () => {

    // =====================================================
    // PLAYER STATE
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
    // SEARCH STATE
    // =====================================================

    const [searchTerm, setSearchTerm] = useState("");

    const [isSearching, setIsSearching] = useState(false);


    // =====================================================
    // TEAM FILTER STATE
    // =====================================================

    const [selectedTeam, setSelectedTeam] = useState("");

    const [isTeamFiltering, setIsTeamFiltering] = useState(false);


    // =====================================================
    // IPL TEAMS
    // =====================================================

    const teams = [
        {
            id: 1,
            name: "Chennai Super Kings",
            shortName: "CSK"
        },
        {
            id: 2,
            name: "Delhi Capitals",
            shortName: "DC"
        },
        {
            id: 3,
            name: "Gujarat Titans",
            shortName: "GT"
        },
        {
            id: 4,
            name: "Kolkata Knight Riders",
            shortName: "KKR"
        },
        {
            id: 5,
            name: "Lucknow Super Giants",
            shortName: "LSG"
        },
        {
            id: 6,
            name: "Mumbai Indians",
            shortName: "MI"
        },
        {
            id: 7,
            name: "Punjab Kings",
            shortName: "PBKS"
        },
        {
            id: 8,
            name: "Rajasthan Royals",
            shortName: "RR"
        },
        {
            id: 9,
            name: "Royal Challengers Bengaluru",
            shortName: "RCB"
        },
        {
            id: 10,
            name: "Sunrisers Hyderabad",
            shortName: "SRH"
        }
    ];


    // =====================================================
    // LOAD PLAYERS
    // =====================================================

    const loadPlayers = async (page = 0) => {

        try {

            setLoading(true);

            setError("");

            const response = await getAllPlayers(page);

            const data = response.data;


            if (!data) {

                throw new Error("Empty response from server.");

            }


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

        catch (err) {

            console.error(
                "Load players error:",
                err
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

        loadPlayers(0);

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

        catch (err) {

            console.error(
                "Load player error:",
                err
            );

            setError(
                "Failed to load player."
            );

        }

    };


    // =====================================================
    // SEARCH PLAYERS
    // =====================================================

    const handleSearch = async () => {

        const name = searchTerm.trim();


        // -----------------------------
        // Empty Search
        // -----------------------------

        if (name === "") {

            setIsSearching(false);

            setError("");

            setIsTeamFiltering(false);

            setSelectedTeam("");

            await loadPlayers(0);

            return;

        }


        try {

            setLoading(true);

            setError("");


            // Search mode ON

            setIsSearching(true);


            // Team filter OFF

            setIsTeamFiltering(false);

            setSelectedTeam("");


            const response =
                await searchPlayers(name);


            setPlayers(

                Array.isArray(response.data)

                    ? response.data

                    : []

            );


            setTotalPages(0);

            setTotalPlayers(
                Array.isArray(response.data)
                    ? response.data.length
                    : 0
            );

        }

        catch (err) {

            console.error(
                "Search players error:",
                err
            );

            setPlayers([]);

            setError(
                "Failed to search players."
            );

        }

        finally {

            setLoading(false);

        }

    };


    // =====================================================
    // CLEAR SEARCH
    // =====================================================

    const handleClearSearch = async () => {

        setSearchTerm("");

        setIsSearching(false);

        setError("");


        await loadPlayers(0);

    };


    // =====================================================
    // SEARCH ENTER KEY
    // =====================================================

    const handleSearchKeyDown = (event) => {

        if (event.key === "Enter") {

            handleSearch();

        }

    };


    // =====================================================
    // TEAM FILTER
    // =====================================================

    const handleTeamFilter = async (teamId) => {

        setSelectedTeam(teamId);

        setError("");


        // =================================================
        // ALL TEAMS
        // =================================================

        if (teamId === "") {

            setIsTeamFiltering(false);

            setIsSearching(false);

            setSearchTerm("");


            await loadPlayers(0);

            return;

        }


        try {

            setLoading(true);

            setError("");


            // Team filter ON

            setIsTeamFiltering(true);


            // Search OFF

            setIsSearching(false);

            setSearchTerm("");


            const response =
                await getPlayersByTeam(teamId);


            setPlayers(

                Array.isArray(response.data)

                    ? response.data

                    : []

            );


            setTotalPages(0);

            setTotalPlayers(

                Array.isArray(response.data)

                    ? response.data.length

                    : 0

            );

        }

        catch (err) {

            console.error(
                "Team filter error:",
                err
            );

            setPlayers([]);

            setError(
                "Failed to load players for selected team."
            );

        }

        finally {

            setLoading(false);

        }

    };


    // =====================================================
    // PLAYER SAVED
    // =====================================================

    const handlePlayerSaved = async () => {

        try {

            setError("");


            // =================================================
            // IF TEAM FILTER IS ACTIVE
            // =================================================

            if (
                isTeamFiltering &&
                selectedTeam !== ""
            ) {

                const response =
                    await getPlayersByTeam(
                        selectedTeam
                    );


                setPlayers(
                    response.data
                );

                setTotalPages(0);

                setTotalPlayers(
                    response.data.length
                );

            }


            // =================================================
            // IF SEARCH IS ACTIVE
            // =================================================

            else if (
                isSearching &&
                searchTerm.trim() !== ""
            ) {

                const response =
                    await searchPlayers(
                        searchTerm
                    );


                setPlayers(
                    response.data
                );

                setTotalPages(0);

                setTotalPlayers(
                    response.data.length
                );

            }


            // =================================================
            // NORMAL PAGINATION
            // =================================================

            else {

                await loadPlayers(
                    currentPage
                );

            }

        }

        catch (err) {

            console.error(
                "Refresh players error:",
                err
            );

            setError(
                "Player saved, but failed to refresh player list."
            );

        }

    };


    // =====================================================
    // EDIT COMPLETE
    // =====================================================

    const handleEditComplete = () => {

        setPlayerToEdit(null);

    };


    // =====================================================
    // DELETE PLAYER
    // =====================================================

    const handleDelete = async (id) => {

        const confirmed =
            window.confirm(
                "Are you sure you want to delete this player?"
            );


        if (!confirmed) {

            return;

        }


        try {

            setLoading(true);

            setError("");


            await deletePlayer(id);


            // =================================================
            // TEAM FILTER ACTIVE
            // =================================================

            if (
                isTeamFiltering &&
                selectedTeam !== ""
            ) {

                const response =
                    await getPlayersByTeam(
                        selectedTeam
                    );


                setPlayers(
                    response.data
                );

                setTotalPlayers(
                    response.data.length
                );

            }


            // =================================================
            // SEARCH ACTIVE
            // =================================================

            else if (
                isSearching &&
                searchTerm.trim() !== ""
            ) {

                const response =
                    await searchPlayers(
                        searchTerm
                    );


                setPlayers(
                    response.data
                );

                setTotalPlayers(
                    response.data.length
                );

            }


            // =================================================
            // NORMAL LIST
            // =================================================

            else {

                // If deleting the last player
                // from a page, move to previous page.

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

        }

        catch (err) {

            console.error(
                "Delete player error:",
                err
            );

            setError(
                "Failed to delete player."
            );

        }

        finally {

            setLoading(false);

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


        // Pagination means normal mode

        setIsSearching(false);

        setIsTeamFiltering(false);

        setSelectedTeam("");

        setSearchTerm("");


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
    // MAIN UI
    // =====================================================

    return (

        <div className="player-container">


            {/* =================================================
                TITLE
            ================================================== */}

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


                onPlayerSaved={
                    handlePlayerSaved
                }


                onEditComplete={
                    handleEditComplete
                }

            />


            <hr />


            {/* =================================================
                SEARCH
            ================================================== */}

            <div className="search-section">

                <h3>
                    Search Players
                </h3>


                <input

                    type="text"

                    placeholder="Enter player name"

                    value={searchTerm}

                    onChange={(event) =>
                        setSearchTerm(
                            event.target.value
                        )
                    }

                    onKeyDown={
                        handleSearchKeyDown
                    }

                />


                <button
                    onClick={handleSearch}
                    disabled={loading}
                >
                    Search
                </button>


                <button
                    onClick={handleClearSearch}
                    disabled={loading}
                >
                    Clear
                </button>

            </div>


            {/* =================================================
                TEAM FILTER
            ================================================== */}

            <div className="team-filter-section">

                <h3>
                    Filter by Team
                </h3>


                <select

                    value={selectedTeam}

                    onChange={(event) =>
                        handleTeamFilter(
                            event.target.value
                        )
                    }

                    disabled={loading}

                >

                    <option value="">
                        All Teams
                    </option>


                    {teams.map((team) => (

                        <option

                            key={team.id}

                            value={team.id}

                        >

                            {team.shortName}
                            {" - "}
                            {team.name}

                        </option>

                    ))}

                </select>

            </div>


            {/* =================================================
                PLAYER COUNT
            ================================================== */}

            <p>

                <strong>
                    Total Players:
                </strong>

                {" "}

                {totalPlayers}

            </p>


            {/* =================================================
                PAGE INFORMATION
            ================================================== */}

            {!isSearching &&
                !isTeamFiltering &&
                totalPages > 0 && (

                    <p>

                        Page{" "}

                        <strong>
                            {currentPage + 1}
                        </strong>

                        {" "}of{" "}

                        <strong>
                            {totalPages}
                        </strong>

                    </p>

                )}


            {/* =================================================
                REFRESH
            ================================================== */}

            <button

                onClick={() => {

                    setIsSearching(false);

                    setIsTeamFiltering(false);

                    setSearchTerm("");

                    setSelectedTeam("");

                    loadPlayers(
                        currentPage
                    );

                }}

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

                <table className="player-table">

                    <thead>

                        <tr>

                            <th>
                                ID
                            </th>

                            <th>
                                Jersey
                            </th>

                            <th>
                                Player Name
                            </th>

                            <th>
                                Runs
                            </th>

                            <th>
                                Wickets
                            </th>

                            <th>
                                Specialization
                            </th>

                            <th>
                                Team
                            </th>

                            <th>
                                Actions
                            </th>

                        </tr>

                    </thead>


                    <tbody>

                        {players.map(
                            (player) => (

                                <tr
                                    key={
                                        player.playerId
                                    }
                                >

                                    <td>
                                        {
                                            player.playerId
                                        }
                                    </td>


                                    <td>
                                        {
                                            player.jerseyNumber
                                        }
                                    </td>


                                    <td>
                                        {
                                            player.playerName
                                        }
                                    </td>


                                    <td>
                                        {
                                            player.runs
                                        }
                                    </td>


                                    <td>
                                        {
                                            player.wickets
                                        }
                                    </td>


                                    <td>
                                        {
                                            player.specialization
                                        }
                                    </td>


                                    <td>
                                        {
                                            player.teamName
                                        }
                                    </td>


                                    <td>

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

                                    </td>

                                </tr>

                            )
                        )}

                    </tbody>

                </table>

            )}


            {/* =================================================
                PAGINATION
            ================================================== */}

            {!isSearching &&
                !isTeamFiltering &&
                totalPages > 1 && (

                    <div>

                        <hr />


                        <h3>
                            Pagination
                        </h3>


                        {/* Previous */}

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


                        {/* Page Numbers */}

                        {Array.from(

                            {
                                length:
                                    totalPages
                            },

                            (_, index) =>
                                index

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


                        {/* Next */}

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
                            </strong>

                            {" "}of{" "}

                            <strong>
                                {totalPages}
                            </strong>

                        </p>

                    </div>

                )}

        </div>

    );

};


export default Player;