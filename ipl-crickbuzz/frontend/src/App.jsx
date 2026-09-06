import { useState } from "react";

import Dashboard from "./pages/Dashboard";
import Players from "./pages/Player";
import Team from "./pages/Team";
import Footer from "./components/Footer";
import "./styles/App.css";

function App() {

    const [currentPage, setCurrentPage] = useState("dashboard");

    return (
        <div className="app">

            {/* ================= NAVBAR ================= */}

            <nav className="navbar">

                <div className="navbar-container">

                    {/* Logo */}

                    <div
                        className="navbar-brand"
                        onClick={() => setCurrentPage("dashboard")}
                    >
                        <span className="brand-icon">
                            🏏
                        </span>

                        <span className="brand-text">
                            IPL Crickbuzz
                        </span>
                    </div>


                    {/* Navigation */}

                    <div className="navbar-links">

                        <button
                            className={
                                currentPage === "dashboard"
                                    ? "nav-link active"
                                    : "nav-link"
                            }
                            onClick={() => setCurrentPage("dashboard")}
                        >
                            Dashboard
                        </button>


                        <button
                            className={
                                currentPage === "players"
                                    ? "nav-link active"
                                    : "nav-link"
                            }
                            onClick={() => setCurrentPage("players")}
                        >
                            Players
                        </button>


                        <button
                            className={
                                currentPage === "teams"
                                    ? "nav-link active"
                                    : "nav-link"
                            }
                            onClick={() => setCurrentPage("teams")}
                        >
                            Teams
                        </button>

                    </div>

                </div>

            </nav>


            {/* ================= PAGE CONTENT ================= */}

            <main className="app-content">

    {currentPage === "dashboard" && <Dashboard />}

    {currentPage === "players" && <Players />}

    {currentPage === "teams" && <Team />}

</main>

<Footer />

        </div>
    );
}

export default App;