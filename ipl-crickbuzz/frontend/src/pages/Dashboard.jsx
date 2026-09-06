import { useEffect, useState } from "react";
import { getDashboardData } from "../services/dashboardService";
import "../styles/Dashboard.css";

function Dashboard() {
    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        loadDashboard();
    }, []);

    const loadDashboard = async () => {
        try {
            setLoading(true);
            setError("");

            const response = await getDashboardData();

            setDashboard(response.data);
        } catch (err) {
            console.error("Error loading dashboard:", err);
            setError("Failed to load dashboard.");
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <div className="dashboard-page">
                <div className="dashboard-loading">
                    <h2>Loading Dashboard...</h2>
                    <p>Fetching IPL statistics</p>
                </div>
            </div>
        );
    }

    if (error) {
        return (
            <div className="dashboard-page">
                <div className="dashboard-error">
                    <h2>{error}</h2>

                    <button onClick={loadDashboard}>
                        Try Again
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="dashboard-page">

            {/* ================= HERO SECTION ================= */}

            <section className="dashboard-hero">

                <div className="hero-content">

                    <span className="hero-badge">
                        🏏 IPL CRICKBUZZ
                    </span>

                    <h1>
                        Welcome to the
                        <span> Cricket Dashboard</span>
                    </h1>

                    <p>
                        Track teams, players and IPL statistics
                        from one place.
                    </p>

                </div>

                <div className="hero-ball">
                    🏏
                </div>

            </section>


            {/* ================= STATISTICS ================= */}

            <section className="dashboard-section">

                <div className="section-heading">
                    <div>
                        <span>OVERVIEW</span>
                        <h2>Tournament Statistics</h2>
                    </div>
                </div>

                <div className="stats-grid">

                    <div className="stat-card">
                        <div className="stat-icon">
                            🏆
                        </div>

                        <div>
                            <p className="stat-label">
                                TOTAL TEAMS
                            </p>

                            <h3>
                                {dashboard.totalTeams}
                            </h3>
                        </div>
                    </div>


                    <div className="stat-card">
                        <div className="stat-icon">
                            👥
                        </div>

                        <div>
                            <p className="stat-label">
                                TOTAL PLAYERS
                            </p>

                            <h3>
                                {dashboard.totalPlayers}
                            </h3>
                        </div>
                    </div>


                    <div className="stat-card">
                        <div className="stat-icon">
                            🏏
                        </div>

                        <div>
                            <p className="stat-label">
                                TOTAL RUNS
                            </p>

                            <h3>
                                {dashboard.totalRuns.toLocaleString()}
                            </h3>
                        </div>
                    </div>


                    <div className="stat-card">
                        <div className="stat-icon">
                            🎯
                        </div>

                        <div>
                            <p className="stat-label">
                                TOTAL WICKETS
                            </p>

                            <h3>
                                {dashboard.totalWickets}
                            </h3>
                        </div>
                    </div>

                </div>

            </section>


            {/* ================= TOP PERFORMERS ================= */}

            <section className="dashboard-section">

                <div className="section-heading">
                    <div>
                        <span>TOP PERFORMERS</span>
                        <h2>Leading Players</h2>
                    </div>
                </div>


                <div className="performers-grid">

                    {/* ORANGE CAP */}

                    <div className="performer-card orange-card">

                        <div className="performer-top">

                            <span className="performer-badge">
                                🟠 ORANGE CAP
                            </span>

                            <span className="performer-number">
                                #1
                            </span>

                        </div>


                        <div className="performer-content">

                            <div className="player-avatar">
                                {dashboard.topRunScorer.playerName
                                    .charAt(0)
                                    .toUpperCase()}
                            </div>

                            <div className="player-details">

                                <h3>
                                    {dashboard.topRunScorer.playerName}
                                </h3>

                                <p>
                                    {dashboard.topRunScorer.teamName}
                                </p>

                                <span>
                                    {dashboard.topRunScorer.specialization}
                                </span>

                            </div>

                        </div>


                        <div className="performer-stat">

                            <strong>
                                {dashboard.topRunScorer.runs}
                            </strong>

                            <span>
                                RUNS
                            </span>

                        </div>

                    </div>


                    {/* PURPLE CAP */}

                    <div className="performer-card purple-card">

                        <div className="performer-top">

                            <span className="performer-badge">
                                🟣 PURPLE CAP
                            </span>

                            <span className="performer-number">
                                #1
                            </span>

                        </div>


                        <div className="performer-content">

                            <div className="player-avatar">
                                {dashboard.topWicketTaker.playerName
                                    .charAt(0)
                                    .toUpperCase()}
                            </div>

                            <div className="player-details">

                                <h3>
                                    {dashboard.topWicketTaker.playerName}
                                </h3>

                                <p>
                                    {dashboard.topWicketTaker.teamName}
                                </p>

                                <span>
                                    {dashboard.topWicketTaker.specialization}
                                </span>

                            </div>

                        </div>


                        <div className="performer-stat">

                            <strong>
                                {dashboard.topWicketTaker.wickets}
                            </strong>

                            <span>
                                WICKETS
                            </span>

                        </div>

                    </div>

                </div>

            </section>


            {/* ================= PLAYER SUMMARY ================= */}

            <section className="dashboard-section">

                <div className="section-heading">
                    <div>
                        <span>PLAYER SUMMARY</span>
                        <h2>Performance Leaders</h2>
                    </div>
                </div>


                <div className="leader-grid">

                    <div className="leader-card">

                        <div className="leader-icon">
                            🏏
                        </div>

                        <div className="leader-info">

                            <p>
                                Highest Run Scorer
                            </p>

                            <h3>
                                {dashboard.topRunScorer.playerName}
                            </h3>

                            <span>
                                {dashboard.topRunScorer.runs} Runs
                            </span>

                        </div>

                    </div>


                    <div className="leader-card">

                        <div className="leader-icon">
                            🎯
                        </div>

                        <div className="leader-info">

                            <p>
                                Highest Wicket Taker
                            </p>

                            <h3>
                                {dashboard.topWicketTaker.playerName}
                            </h3>

                            <span>
                                {dashboard.topWicketTaker.wickets} Wickets
                            </span>

                        </div>

                    </div>

                </div>

            </section>


            {/* ================= FOOTER ================= */}

            <footer className="dashboard-footer">

                <h3>
                    IPL Crickbuzz
                </h3>

                <p>
                    A Spring Boot + React IPL statistics application
                </p>

            </footer>

        </div>
    );
}

export default Dashboard;