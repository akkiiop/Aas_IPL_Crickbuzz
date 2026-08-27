import { useEffect, useState } from "react";
import { createPlayer, updatePlayer } from "../services/playerService";

function PlayerForm({playerToEdit, onPlayerSaved, onEditComplete}) {
	
	const [player, setPlayer] = useState({
        jerseyNumber: "",
        playerName: "",
        runs: "",
        wickets: "",
        teamName: "",
        specialization: ""
    });

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    

    useEffect(()=>{
        if(playerToEdit){
            setPlayer(playerToEdit);
        }
    }, [playerToEdit]);


    const handleChange = (e) => {
        const {name, value} = e.target;

        setPlayer({
            ...player,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError("");
        setSuccess("");

        try {
            if (player.playerName.trim() === "") {
                setError("Player name is required.");
                return;    
            }
            if(player.runs < 0 || player.wickets < 0 || player.jerseyNumber < 0){
                setError("Runs, Wickets and Jersey Number cannot be negative.");
                return;
            }
            
            if(player.teamName.trim() === ""){
                setError("Team name is required.");
                return;
            }

            if(player.specialization.trim() === ""){
                setError("Specialization is required.");
                return;
            }

            const playerData = {
                ...player,
                runs: parseInt(player.runs),
                wickets: parseInt(player.wickets),
                jerseyNumber: parseInt(player.jerseyNumber)
            };

            if(playerToEdit){
                await updatePlayer(
                    playerToEdit.playerId,
                    playerData
                );
                setSuccess("Player updated successfully.");

                if(onEditComplete){
                    onEditComplete();
                }
            } else{
                await createPlayer(playerData);
                setSuccess("Player created successfully.");
            }
        
    

            setPlayer({
                jerseyNumber: "",
                playerName: "",
                runs: "",
                wickets: "",
                teamName: "",
                specialization: ""
            });

            if (onPlayerSaved){
                onPlayerSaved();
            }
        } catch (error) {
            console.error(error);
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

            {error && <p>{error}</p>}

            {success && <p>{success}</p>}

            <form onSubmit={handleSubmit}>

                <div>
                    <label>Jersey Number</label>
                <input
                        type="number"
                        name="jerseyNumber"
                        value={player.jerseyNumber}
                        onChange={handleChange}
                    />
                </div>

                <div>
                    <label>Player Name</label>

                    <input
                        type="text"
                        name="playerName"
                        value={player.playerName}
                        onChange={handleChange}
                    />
                </div>

                <div>
                    <label>Runs</label>

                    <input
                        type="number"
                        name="runs"
                        value={player.runs}
                        onChange={handleChange}
                    />
                </div>

                <div>
                    <label>Wickets</label>

                    <input
                        type="number"
                        name="wickets"
                        value={player.wickets}
                        onChange={handleChange}
                    />
                </div>

                <div>
                    <label>Team Name</label>

                    <input
                        type="text"
                        name="teamName"
                        value={player.teamName}
                        onChange={handleChange}
                    />
                </div>

                <div>
                    <label>Specialization</label>

                    <input
                        type="text"
                        name="specialization"
                        value={player.specialization}
                        onChange={handleChange}
                    />
                </div>

                <button type="submit">
                    {playerToEdit ? "Update Player" : "Add Player"}
                </button>
            </form>
        </div>
    )
}

export default PlayerForm;