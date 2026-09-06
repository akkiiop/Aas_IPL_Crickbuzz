import { useEffect, useState } from "react";

import {
    getAllTeams,
    getPlayersByTeam,
    createTeam,
    updateTeam,
    deleteTeam
} from "../services/teamService";

import TeamForm from "../components/TeamForm";

import "../styles/Team.css";


function Team() {

    const [teams, setTeams] = useState([]);

    const [selectedTeam, setSelectedTeam] =
        useState(null);

    const [players, setPlayers] =
        useState([]);

    const [loading, setLoading] =
        useState(true);

    const [playersLoading, setPlayersLoading] =
        useState(false);

    const [error, setError] =
        useState("");

    const [showForm, setShowForm] =
        useState(false);

    const [selectedTeamForEdit, setSelectedTeamForEdit] =
        useState(null);


    // =====================================================
    // LOAD TEAMS
    // =====================================================

    useEffect(() => {

        loadTeams();

    }, []);


    const loadTeams = async () => {

        try {

            setLoading(true);

            setError("");

            const response =
                await getAllTeams();

            setTeams(
                Array.isArray(response.data)
                    ? response.data
                    : []
            );

        }

        catch (err) {

            console.error(
                "Error loading teams:",
                err
            );

            setTeams([]);

            setError(
                "Failed to load teams."
            );

        }

        finally {

            setLoading(false);

        }

    };


    // =====================================================
    // VIEW PLAYERS
    // =====================================================

    const handleViewPlayers = async (team) => {

        try {

            setSelectedTeam(team);

            setPlayersLoading(true);

            setError("");

            const response =
                await getPlayersByTeam(
                    team.teamId
                );

            setPlayers(
                Array.isArray(response.data)
                    ? response.data
                    : []
            );

        }

        catch (err) {

            console.error(
                "Error loading players:",
                err
            );

            setPlayers([]);

            setError(
                "Failed to load players."
            );

        }

        finally {

            setPlayersLoading(false);

        }

    };


    // =====================================================
    // CLOSE PLAYERS
    // =====================================================

    const handleClosePlayers = () => {

        setSelectedTeam(null);

        setPlayers([]);

    };


    // =====================================================
    // ADD / UPDATE TEAM
    // =====================================================

    const handleSaveTeam = async (team) => {

        try {

            setError("");

            if (selectedTeamForEdit) {

                await updateTeam(
                    selectedTeamForEdit.teamId,
                    team
                );

                alert(
                    "Team updated successfully!"
                );

            }

            else {

                await createTeam(team);

                alert(
                    "Team created successfully!"
                );

            }


            setShowForm(false);

            setSelectedTeamForEdit(null);

            await loadTeams();

        }

        catch (err) {

            console.error(
                "Error saving team:",
                err
            );

            alert(
                "Failed to save team."
            );

        }

    };


    // =====================================================
    // CANCEL FORM
    // =====================================================

    const handleCancelForm = () => {

        setShowForm(false);

        setSelectedTeamForEdit(null);

    };


    // =====================================================
    // ADD TEAM
    // =====================================================

    const handleAddTeam = () => {

        setSelectedTeamForEdit(null);

        setShowForm(true);

    };


    // =====================================================
    // EDIT TEAM
    // =====================================================

    const handleEditTeam = (team) => {

        setSelectedTeamForEdit(team);

        setShowForm(true);

        window.scrollTo({

            top: 0,

            behavior: "smooth"

        });

    };


    // =====================================================
    // DELETE TEAM
    // =====================================================

    const handleDeleteTeam = async (team) => {

        const confirmDelete =
            window.confirm(
                `Are you sure you want to delete ${team.teamName}?`
            );


        if (!confirmDelete) {

            return;

        }


        try {

            setError("");

            await deleteTeam(
                team.teamId
            );

            alert(
                "Team deleted successfully!"
            );


            if (
                selectedTeam &&
                selectedTeam.teamId === team.teamId
            ) {

                handleClosePlayers();

            }


            await loadTeams();

        }

        catch (err) {

            console.error(
                "Error deleting team:",
                err
            );

            alert(
                "Failed to delete team. " +
                "Make sure the team has no players."
            );

        }

    };


    // =====================================================
    // LOADING
    // =====================================================

    if (loading) {

        return (

            <div className="team-page">

                <div className="team-loading">

                    <div className="team-spinner"></div>

                    <h2>
                        Loading Teams
                    </h2>

                    <p>
                        Fetching IPL team information...
                    </p>

                </div>

            </div>

        );

    }


    // =====================================================
    // ERROR
    // =====================================================

    if (
        error &&
        teams.length === 0
    ) {

        return (

            <div className="team-page">

                <div className="team-error-page">

                    <div className="team-error-icon">
                        ⚠️
                    </div>

                    <h2>
                        Unable to Load Teams
                    </h2>

                    <p>
                        {error}
                    </p>

                    <button
                        className="retry-team-btn"
                        onClick={loadTeams}
                    >
                        ↻ Try Again
                    </button>

                </div>

            </div>

        );

    }


    // =====================================================
    // MAIN UI
    // =====================================================

    return (

        <div className="team-page">


            {/* =================================================
                HERO
            ================================================== */}

            <section className="team-hero">

                <div className="team-hero-content">

                    <span className="team-hero-label">
                        IPL CRICKBUZZ
                    </span>

                    <h1>
                        IPL Teams
                    </h1>

                    <p>
                        Explore teams, captains, home grounds
                        and complete player squads.
                    </p>

                </div>


                <div className="team-hero-ball">
                    🏏
                </div>

            </section>


            {/* =================================================
                TEAM MANAGEMENT HEADER
            ================================================== */}

            <section className="team-management">

                <div>

                    <span>
                        TEAM MANAGEMENT
                    </span>

                    <h2>
                        IPL Team Directory
                    </h2>

                    <p>
                        Manage your IPL teams and view their squads.
                    </p>

                </div>


                <button
                    className="add-team-btn"
                    onClick={handleAddTeam}
                >

                    + Add Team

                </button>

            </section>


            {/* =================================================
                TEAM FORM
            ================================================== */}

            {showForm && (

                <section className="team-form-section">

                    <div className="team-form-header">

                        <div>

                            <span>
                                {selectedTeamForEdit
                                    ? "EDIT TEAM"
                                    : "TEAM MANAGEMENT"}
                            </span>

                            <h2>

                                {selectedTeamForEdit
                                    ? "Update Team"
                                    : "Add New Team"}

                            </h2>

                        </div>


                        <button
                            type="button"
                            className="team-close-form-btn"
                            onClick={handleCancelForm}
                        >

                            − Close Form

                        </button>

                    </div>


                    <div className="team-form-content">

                        <TeamForm
                            teamToEdit={
                                selectedTeamForEdit
                            }
                            onSave={
                                handleSaveTeam
                            }
                            onCancel={
                                handleCancelForm
                            }
                        />

                    </div>

                </section>

            )}


            {/* =================================================
                ERROR MESSAGE
            ================================================== */}

            {error && teams.length > 0 && (

                <div className="team-error">

                    ⚠️ {error}

                </div>

            )}


            {/* =================================================
                TEAM LIST HEADER
            ================================================== */}

            <div className="team-list-header">

                <div>

                    <span>
                        IPL TEAMS
                    </span>

                    <h2>
                        Teams & Squads
                    </h2>

                </div>


                <div className="team-count">

                    <strong>
                        {teams.length}
                    </strong>

                    <span>
                        Teams
                    </span>

                </div>

            </div>


            {/* =================================================
                TEAM CARDS
            ================================================== */}

            {teams.length === 0 ? (

                <div className="no-teams">

                    <div className="no-teams-icon">
                        🏏
                    </div>

                    <h3>
                        No Teams Found
                    </h3>

                    <p>
                        Add your first IPL team to get started.
                    </p>

                    <button
                        className="add-team-btn"
                        onClick={handleAddTeam}
                    >
                        + Add Team
                    </button>

                </div>

            ) : (

                <div className="team-grid">

                    {teams.map((team) => (

                        <article
                            key={team.teamId}
                            className="team-card"
                        >


                            {/* CARD TOP */}

                            <div className="team-card-top">

                                <div className="team-logo">

                                    {
                                        team.shortName
                                            ?.substring(0, 3)
                                            ?.toUpperCase()
                                    }

                                </div>


                                <div className="team-card-heading">

                                    <span>
                                        {team.shortName}
                                    </span>

                                    <h2>
                                        {team.teamName}
                                    </h2>

                                </div>

                            </div>


                            {/* CARD INFORMATION */}

                            <div className="team-details">

                                <div className="team-detail">

                                    <span className="detail-icon">
                                        👤
                                    </span>

                                    <div>

                                        <small>
                                            Captain
                                        </small>

                                        <strong>
                                            {team.captain}
                                        </strong>

                                    </div>

                                </div>


                                <div className="team-detail">

                                    <span className="detail-icon">
                                        📍
                                    </span>

                                    <div>

                                        <small>
                                            City
                                        </small>

                                        <strong>
                                            {team.city}
                                        </strong>

                                    </div>

                                </div>


                                <div className="team-detail">

                                    <span className="detail-icon">
                                        🏟️
                                    </span>

                                    <div>

                                        <small>
                                            Home Ground
                                        </small>

                                        <strong>
                                            {team.homeGround}
                                        </strong>

                                    </div>

                                </div>

                            </div>


                            {/* CARD ACTIONS */}

                            <div className="team-actions">

                                <button
                                    className="view-players-btn"
                                    onClick={() =>
                                        handleViewPlayers(team)
                                    }
                                >

                                    View Players

                                </button>


                                <button
                                    className="edit-btn"
                                    onClick={() =>
                                        handleEditTeam(team)
                                    }
                                >

                                    ✏ Edit

                                </button>


                                <button
                                    className="delete-btn"
                                    onClick={() =>
                                        handleDeleteTeam(team)
                                    }
                                >

                                    🗑 Delete

                                </button>

                            </div>

                        </article>

                    ))}

                </div>

            )}


            {/* =================================================
                SELECTED TEAM PLAYERS
            ================================================== */}

            {selectedTeam && (

                <section className="players-section">


                    <div className="players-header">

                        <div>

                            <span>
                                TEAM SQUAD
                            </span>

                            <h2>
                                {selectedTeam.teamName}
                            </h2>

                            <p>
                                {selectedTeam.city}
                                {" • "}
                                {selectedTeam.homeGround}
                            </p>

                        </div>


                        <button
                            className="close-btn"
                            onClick={
                                handleClosePlayers
                            }
                        >

                            ✕ Close

                        </button>

                    </div>


                    {/* PLAYER LOADING */}

                    {playersLoading ? (

                        <div className="players-loading">

                            <div className="team-spinner"></div>

                            <p>
                                Loading squad...
                            </p>

                        </div>

                    ) : players.length === 0 ? (

                        <div className="no-team-players">

                            <div>
                                🏏
                            </div>

                            <h3>
                                No Players Found
                            </h3>

                            <p>
                                This team currently has no players.
                            </p>

                        </div>

                    ) : (

                        <div className="players-table-wrapper">

                            <table className="players-table">

                                <thead>

                                    <tr>

                                        <th>
                                            JERSEY
                                        </th>

                                        <th>
                                            PLAYER
                                        </th>

                                        <th>
                                            RUNS
                                        </th>

                                        <th>
                                            WICKETS
                                        </th>

                                        <th>
                                            ROLE
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

                                                    <span className="jersey-badge">
                                                        #{player.jerseyNumber}
                                                    </span>

                                                </td>


                                                <td>

                                                    <div className="squad-player">

                                                        <div className="squad-avatar">

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
                                                                Player ID #
                                                                {" "}
                                                                {
                                                                    player.playerId
                                                                }
                                                            </small>

                                                        </div>

                                                    </div>

                                                </td>


                                                <td>

                                                    <strong className="team-runs">
                                                        {
                                                            player.runs
                                                        }
                                                    </strong>

                                                </td>


                                                <td>

                                                    <strong className="team-wickets">
                                                        {
                                                            player.wickets
                                                        }
                                                    </strong>

                                                </td>


                                                <td>

                                                    <span className="team-role">
                                                        {
                                                            player.specialization
                                                        }
                                                    </span>

                                                </td>

                                            </tr>

                                        )
                                    )}

                                </tbody>

                            </table>

                        </div>

                    )}

                </section>

            )}


            {/* =================================================
                FOOTER
            ================================================== */}

            <footer className="team-footer">

                <strong>
                    IPL Crickbuzz
                </strong>

                <span>
                    Team Management & Squad Statistics
                </span>

            </footer>


        </div>

    );

}


export default Team;