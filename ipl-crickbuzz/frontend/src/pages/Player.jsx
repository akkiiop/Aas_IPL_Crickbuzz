import { useEffect, useState } from "react";
import { getAllPlayers, getPlayerById, deletePlayer} from "../services/playerService";
import  PlayerForm from "../components/PlayerForm";
function Players() {

    const [players, setPlayers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [playerToEdit, setPlayerToEdit] = useState(null);
    const loadPlayers = async () => {

        try {

            const response = await getAllPlayers();

            setPlayers(response.data);

        } catch (error) {

            console.error(error);

            setError("Failed to load players.");

        } finally {

            setLoading(false);

        }
    };

    useEffect(() => {

        loadPlayers();

    }, []);

    if (loading) {

        return <h2>Loading players...</h2>;

    }

    if (error) {

        return <h2>{error}</h2>;

    }

    const handleEdit = async (id) => {
        try {
            const response = await getPlayerById(id);

            setPlayerToEdit(response.data);
        } catch (error){
            console.error(error);
        }
    }


    const handleDelete = async (id) => {

        const confirmed = window.confirm(
            "Are you sure you want to delete this player?"
        );

        if(!confirmed){
            return;
        }
        
        try {
            await deletePlayer(id);
            await loadPlayers();
        } catch(error){
            console.error("Delete player error:",error);
        }
    };

    return (

        <div>

            <h1>IPL Players</h1>
            <PlayerForm 
                playerToEdit={playerToEdit}
                onPlayerSaved={loadPlayers}
                onEditComplete={() => setPlayerToEdit(null)}
            />

            <hr/>

            <p>Total Players: {players.length}</p>

            <button onClick={loadPlayers}>Refresh Players</button>

            {players.length === 0 ? (
                <p>No players found.</p>
            ) : (

            players.map((player) => (

                <div key={player.playerId}>

                    <h3>{player.playerName}</h3>

                    <p>Team: {player.teamName}</p>

                    <p>Runs: {player.runs}</p>

                    <p>Wickets: {player.wickets}</p>

                    <button onClick={() => handleEdit(player.playerId)}>
                        Edit
                    </button>

                    <button onClick={() => handleDelete(player.playerId)}>
                        Delete
                    </button>

                </div>
            ))

            )}

        </div>

    );
}

export default Players;