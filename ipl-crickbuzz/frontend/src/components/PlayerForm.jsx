import { useEffect, useState } from "react";
import { createPlayer, updatePlayer } from "../services/playerService";

const teams = [
    { id: 1, name: "Chennai Super Kings" },
    { id: 2, name: "Delhi Capitals" },
    { id: 3, name: "Gujarat Titans" },
    { id: 4, name: "Kolkata Knight Riders" },
    { id: 5, name: "Lucknow Super Giants" },
    { id: 6, name: "Mumbai Indians" },
    { id: 7, name: "Punjab Kings" },
    { id: 8, name: "Rajasthan Royals" },
    { id: 9, name: "Royal Challengers Bengaluru" },
    { id: 10, name: "Sunrisers Hyderabad" }
];

const emptyPlayer = {
    jerseyNumber: "",
    playerName: "",
    runs: "",
    wickets: "",
    teamId: "",
    specialization: ""
};

function PlayerForm({ playerToEdit, onPlayerSaved, onEditComplete }) {

    const [player, setPlayer] = useState(emptyPlayer);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

	useEffect(() => {

	    if (playerToEdit) {

	        // Convert teamName returned by backend into teamId
	        const selectedTeam = teams.find(
	            (team) => team.name === playerToEdit.teamName
	        );

	        // eslint-disable-next-line react-hooks/set-state-in-effect
	        setPlayer({
	            jerseyNumber: playerToEdit.jerseyNumber ?? "",
	            playerName: playerToEdit.playerName ?? "",
	            runs: playerToEdit.runs ?? "",
	            wickets: playerToEdit.wickets ?? "",
	            teamId: selectedTeam ? selectedTeam.id : "",
	            specialization: playerToEdit.specialization ?? ""
	        });

	        // eslint-disable-next-line react-hooks/set-state-in-effect
	        setSuccess("");

	        // eslint-disable-next-line react-hooks/set-state-in-effect
	        setError("");

	    } else {

	        // eslint-disable-next-line react-hooks/set-state-in-effect
	        setPlayer(emptyPlayer);

	        // eslint-disable-next-line react-hooks/set-state-in-effect
	        setSuccess("");

	        // eslint-disable-next-line react-hooks/set-state-in-effect
	        setError("");

	    }

	}, [playerToEdit]);


    const handleChange = (e) => {

        const { name, value } = e.target;

        setPlayer((previousPlayer) => ({
            ...previousPlayer,
            [name]: value
        }));

    };


    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");
        setSuccess("");


        // -------------------------
        // Validation
        // -------------------------

        if (player.playerName.trim() === "") {

            setError("Player name is required.");
            return;

        }

        if (
            player.jerseyNumber === "" ||
            Number(player.jerseyNumber) < 1 ||
            Number(player.jerseyNumber) > 999
        ) {

            setError("Jersey number must be between 1 and 999.");
            return;

        }

        if (
            player.runs === "" ||
            Number(player.runs) < 0
        ) {

            setError("Runs cannot be negative.");
            return;

        }

        if (
            player.wickets === "" ||
            Number(player.wickets) < 0
        ) {

            setError("Wickets cannot be negative.");
            return;

        }

        if (!player.teamId) {

            setError("Please select a team.");
            return;

        }

        if (player.specialization.trim() === "") {

            setError("Specialization is required.");
            return;

        }


        // -------------------------
        // Prepare request
        // -------------------------

        const playerData = {
            jerseyNumber: Number(player.jerseyNumber),
            playerName: player.playerName.trim(),
            runs: Number(player.runs),
            wickets: Number(player.wickets),
            teamId: Number(player.teamId),
            specialization: player.specialization.trim()
        };


        try {

            // -------------------------
            // UPDATE
            // -------------------------

            if (playerToEdit) {

                await updatePlayer(
                    playerToEdit.playerId,
                    playerData
                );

                setSuccess("Player updated successfully.");

                if (onEditComplete) {
                    onEditComplete();
                }

            }

            // -------------------------
            // CREATE
            // -------------------------

            else {

                await createPlayer(playerData);

                setSuccess("Player created successfully.");

            }


            // -------------------------
            // Clear form
            // -------------------------

            setPlayer(emptyPlayer);


            // -------------------------
            // Reload players
            // -------------------------

            if (onPlayerSaved) {
                await onPlayerSaved();
            }

        } catch (error) {

            console.error(
                playerToEdit
                    ? "Update player error:"
                    : "Create player error:",
                error
            );

            if (error.response) {

                console.error(
                    "Backend response:",
                    error.response.data
                );

            }

            setError(
                playerToEdit
                    ? "Failed to update player."
                    : "Failed to create player."
            );

        }

    };


    return (

        <div>

            <h2>
                {playerToEdit ? "Update Player" : "Add Player"}
            </h2>


            {error && (
                <p>
                    {error}
                </p>
            )}


            {success && (
                <p>
                    {success}
                </p>
            )}


            <form onSubmit={handleSubmit}>

                {/* Jersey Number */}

                <div>

                    <label>
                        Jersey Number
                    </label>

                    <input
                        type="number"
                        name="jerseyNumber"
                        value={player.jerseyNumber}
                        onChange={handleChange}
                        min="1"
                        max="999"
                    />

                </div>


                {/* Player Name */}

                <div>

                    <label>
                        Player Name
                    </label>

                    <input
                        type="text"
                        name="playerName"
                        value={player.playerName}
                        onChange={handleChange}
                    />

                </div>


                {/* Runs */}

                <div>

                    <label>
                        Runs
                    </label>

                    <input
                        type="number"
                        name="runs"
                        value={player.runs}
                        onChange={handleChange}
                        min="0"
                    />

                </div>


                {/* Wickets */}

                <div>

                    <label>
                        Wickets
                    </label>

                    <input
                        type="number"
                        name="wickets"
                        value={player.wickets}
                        onChange={handleChange}
                        min="0"
                    />

                </div>


                {/* Team */}

                <div>

                    <label>
                        Team
                    </label>

                    <select
                        name="teamId"
                        value={player.teamId}
                        onChange={handleChange}
                    >

                        <option value="">
                            Select Team
                        </option>

                        {teams.map((team) => (

                            <option
                                key={team.id}
                                value={team.id}
                            >
                                {team.name}
                            </option>

                        ))}

                    </select>

                </div>


                {/* Specialization */}

                <div>

                    <label>
                        Specialization
                    </label>

                    <input
                        type="text"
                        name="specialization"
                        value={player.specialization}
                        onChange={handleChange}
                    />

                </div>


                {/* Submit */}

                <button type="submit">

                    {playerToEdit
                        ? "Update Player"
                        : "Add Player"}

                </button>


                {/* Cancel Edit */}

                {playerToEdit && (

                    <button
                        type="button"
                        onClick={() => {

                            setPlayer(emptyPlayer);
                            setError("");
                            setSuccess("");

                            if (onEditComplete) {
                                onEditComplete();
                            }

                        }}
                    >
                        Cancel
                    </button>

                )}

            </form>

        </div>

    );

}

export default PlayerForm;