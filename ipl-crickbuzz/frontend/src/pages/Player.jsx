import React, { useEffect, useState } from "react";

import {
    getAllPlayers,
    getPlayerById,
    deletePlayer,
    searchPlayers,
    getPlayersByTeam
} from "../services/playerService";

import PlayerForm from "../components/PlayerForm";

import "../styles/Player.css";


const Player = () => {

    // =====================================================
    // PLAYER STATE
    // =====================================================

    const [players, setPlayers] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [playerToEdit, setPlayerToEdit] = useState(null);

    const [showPlayerForm, setShowPlayerForm] = useState(false);


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

                throw new Error(
                    "Empty response from server."
                );

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


            setPlayerToEdit(
                response.data
            );


            setShowPlayerForm(true);


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

        const name =
            searchTerm.trim();


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

            setIsSearching(true);

            setIsTeamFiltering(false);

            setSelectedTeam("");


            const response =
                await searchPlayers(name);


            const data = response.data;


            setPlayers(
                Array.isArray(data)
                    ? data
                    : []
            );


            setTotalPages(0);

            setTotalPlayers(
                Array.isArray(data)
                    ? data.length
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

        setIsTeamFiltering(false);

        setSelectedTeam("");

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

            setIsTeamFiltering(true);

            setIsSearching(false);

            setSearchTerm("");


            const response =
                await getPlayersByTeam(teamId);


            const data = response.data;


            setPlayers(
                Array.isArray(data)
                    ? data
                    : []
            );


            setTotalPages(0);

            setTotalPlayers(
                Array.isArray(data)
                    ? data.length
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


            if (
                isTeamFiltering &&
                selectedTeam !== ""
            ) {

                const response =
                    await getPlayersByTeam(
                        selectedTeam
                    );


                const data = response.data;


                setPlayers(
                    Array.isArray(data)
                        ? data
                        : []
                );


                setTotalPages(0);

                setTotalPlayers(
                    Array.isArray(data)
                        ? data.length
                        : 0
                );

            }

            else if (
                isSearching &&
                searchTerm.trim() !== ""
            ) {

                const response =
                    await searchPlayers(
                        searchTerm
                    );


                const data = response.data;


                setPlayers(
                    Array.isArray(data)
                        ? data
                        : []
                );


                setTotalPages(0);

                setTotalPlayers(
                    Array.isArray(data)
                        ? data.length
                        : 0
                );

            }

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

        setShowPlayerForm(false);

    };


    // =====================================================
    // CLOSE PLAYER FORM
    // =====================================================

    const handleClosePlayerForm = () => {

        setShowPlayerForm(false);

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


            if (
                isTeamFiltering &&
                selectedTeam !== ""
            ) {

                const response =
                    await getPlayersByTeam(
                        selectedTeam
                    );


                const data = response.data;


                setPlayers(
                    Array.isArray(data)
                        ? data
                        : []
                );


                setTotalPages(0);

                setTotalPlayers(
                    Array.isArray(data)
                        ? data.length
                        : 0
                );

            }

            else if (
                isSearching &&
                searchTerm.trim() !== ""
            ) {

                const response =
                    await searchPlayers(
                        searchTerm
                    );


                const data = response.data;


                setPlayers(
                    Array.isArray(data)
                        ? data
                        : []
                );


                setTotalPages(0);

                setTotalPlayers(
                    Array.isArray(data)
                        ? data.length
                        : 0
                );

            }

            else {

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

        if (loading) return;

        if (page < 0) return;

        if (page >= totalPages) return;

        if (page === currentPage) return;


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
    // LOADING
    // =====================================================

    if (
        loading &&
        players.length === 0
    ) {

        return (

            <div className="player-page">

                <div className="player-loading">

                    <div className="loading-spinner"></div>

                    <h2>
                        Loading Players
                    </h2>

                    <p>
                        Fetching player statistics...
                    </p>

                </div>

            </div>

        );

    }


    // =====================================================
    // MAIN UI
    // =====================================================

    return (

        <div className="player-page">


            {/* =================================================
                HERO
            ================================================== */}

            <section className="player-hero">

                <div>

                    <span className="player-hero-label">
                        IPL CRICKBUZZ
                    </span>

                    <h1>
                        IPL Players
                    </h1>

                    <p>
                        Explore player statistics,
                        teams and performances.
                    </p>

                </div>


                <div className="player-hero-icon">
                    🏏
                </div>

            </section>


            {/* =================================================
                PLAYER FORM
            ================================================== */}

            <section className="player-form-section">

                <div className="player-form-header">

                    <div>

                        <span>

                            {playerToEdit
                                ? "EDIT PLAYER"
                                : "PLAYER MANAGEMENT"}

                        </span>


                        <h2>

                            {playerToEdit
                                ? "Update Player"
                                : "Add New Player"}

                        </h2>

                    </div>


                    <button

                        type="button"

                        className={
                            showPlayerForm
                                ? "form-toggle-btn close"
                                : "form-toggle-btn"
                        }

                        onClick={() => {

                            if (showPlayerForm) {

                                handleClosePlayerForm();

                            }

                            else {

                                setShowPlayerForm(true);

                            }

                        }}

                    >

                        {showPlayerForm
                            ? "− Close Form"
                            : "+ Add Player"}

                    </button>

                </div>


                {showPlayerForm && (

                    <div className="player-form-content">

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

                    </div>

                )}

            </section>


            {/* =================================================
                SEARCH / FILTER
            ================================================== */}

            <section className="player-controls">

                <div className="control-header">

                    <div>

                        <span>
                            PLAYER DATABASE
                        </span>

                        <h2>
                            Search & Filter
                        </h2>

                    </div>


                    <div className="player-count">

                        <strong>
                            {totalPlayers}
                        </strong>

                        <span>
                            Players
                        </span>

                    </div>

                </div>


                <div className="controls-row">


                    {/* SEARCH */}

                    <div className="search-box">

                        <span className="search-icon">
                            🔍
                        </span>


                        <input

                            type="text"

                            placeholder="Search player by name..."

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

                    </div>


                    <button

                        className="search-btn"

                        onClick={
                            handleSearch
                        }

                        disabled={loading}

                    >

                        Search

                    </button>


                    <button

                        className="clear-btn"

                        onClick={
                            handleClearSearch
                        }

                        disabled={loading}

                    >

                        Clear

                    </button>


                    {/* TEAM FILTER */}

                    <select

                        className="team-select"

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


                        {teams.map(
                            (team) => (

                                <option
                                    key={team.id}
                                    value={team.id}
                                >

                                    {team.shortName}
                                    {" - "}
                                    {team.name}

                                </option>

                            )
                        )}

                    </select>

                </div>

            </section>


            {/* =================================================
                ERROR
            ================================================== */}

            {error && (

                <div className="player-error">

                    ⚠️ {error}

                </div>

            )}


            {/* =================================================
                PLAYER LIST
            ================================================== */}

            <section className="player-list-section">

                <div className="list-header">

                    <div>

                        <span>
                            SQUAD
                        </span>

                        <h2>
                            Player Statistics
                        </h2>

                    </div>


                    <button

                        className="refresh-btn"

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

                        ↻

                        {loading
                            ? " Refreshing..."
                            : " Refresh"}

                    </button>

                </div>


                {players.length === 0 ? (

                    <div className="no-players">

                        <div>
                            🏏
                        </div>

                        <h3>
                            No Players Found
                        </h3>

                        <p>
                            Try changing your search
                            or team filter.
                        </p>

                    </div>

                ) : (

                    <div className="table-wrapper">

                        <table className="player-table">

                            <thead>

                                <tr>

                                    <th>
                                        #
                                    </th>

                                    <th>
                                        PLAYER
                                    </th>

                                    <th>
                                        TEAM
                                    </th>

                                    <th>
                                        ROLE
                                    </th>

                                    <th>
                                        RUNS
                                    </th>

                                    <th>
                                        WICKETS
                                    </th>

                                    <th>
                                        ACTIONS
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

                                                <span className="player-id">

                                                    {
                                                        player.playerId
                                                    }

                                                </span>

                                            </td>


                                            <td>

                                                <div className="player-name-cell">

                                                    <div className="player-mini-avatar">

                                                        {
                                                            player.playerName
                                                                ?.charAt(0)
                                                                ?.toUpperCase()
                                                        }

                                                    </div>


                                                    <div>

                                                        <strong>
                                                            {
                                                                player.playerName
                                                            }
                                                        </strong>


                                                        <small>

                                                            Jersey #

                                                            {" "}

                                                            {
                                                                player.jerseyNumber
                                                            }

                                                        </small>

                                                    </div>

                                                </div>

                                            </td>


                                            <td>

                                                <span className="team-badge">

                                                    {
                                                        player.teamName
                                                    }

                                                </span>

                                            </td>


                                            <td>

                                                <span className="role-badge">

                                                    {
                                                        player.specialization
                                                    }

                                                </span>

                                            </td>


                                            <td>

                                                <strong className="runs-value">

                                                    {
                                                        player.runs
                                                    }

                                                </strong>

                                            </td>


                                            <td>

                                                <strong className="wickets-value">

                                                    {
                                                        player.wickets
                                                    }

                                                </strong>

                                            </td>


                                            <td>

                                                <div className="action-buttons">

                                                    <button

                                                        className="edit-player-btn"

                                                        onClick={() =>
                                                            handleEdit(
                                                                player.playerId
                                                            )
                                                        }

                                                        disabled={
                                                            loading
                                                        }

                                                    >

                                                        ✏ Edit

                                                    </button>


                                                    <button

                                                        className="delete-player-btn"

                                                        onClick={() =>
                                                            handleDelete(
                                                                player.playerId
                                                            )
                                                        }

                                                        disabled={
                                                            loading
                                                        }

                                                    >

                                                        🗑 Delete

                                                    </button>

                                                </div>

                                            </td>

                                        </tr>

                                    )
                                )}

                            </tbody>

                        </table>

                    </div>

                )}

            </section>


            {/* =================================================
                PAGINATION
            ================================================== */}

            {!isSearching &&
                !isTeamFiltering &&
                totalPages > 1 && (

                    <section className="pagination-section">

                        <button

                            className="pagination-btn"

                            onClick={() =>
                                handlePageChange(
                                    currentPage - 1
                                )
                            }

                            disabled={
                                currentPage === 0 ||
                                loading
                            }

                        >

                            ← Previous

                        </button>


                        <div className="page-numbers">

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

                                        className={
                                            currentPage === page
                                                ? "page-number active"
                                                : "page-number"
                                        }

                                        onClick={() =>
                                            handlePageChange(
                                                page
                                            )
                                        }

                                        disabled={
                                            loading
                                        }

                                    >

                                        {page + 1}

                                    </button>

                                )
                            )}

                        </div>


                        <button

                            className="pagination-btn"

                            onClick={() =>
                                handlePageChange(
                                    currentPage + 1
                                )
                            }

                            disabled={
                                currentPage ===
                                    totalPages - 1 ||
                                loading
                            }

                        >

                            Next →

                        </button>

                    </section>

                )}


            {!isSearching &&
                !isTeamFiltering &&
                totalPages > 0 && (

                    <p className="pagination-info">

                        Showing page{" "}

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
                FOOTER
            ================================================== */}

            <footer className="player-footer">

                <strong>
                    IPL Crickbuzz
                </strong>

                <span>
                    Player Management & Statistics
                </span>

            </footer>


        </div>

    );

};


export default Player;