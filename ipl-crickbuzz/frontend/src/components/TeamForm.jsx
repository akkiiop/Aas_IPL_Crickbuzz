import { useEffect, useState } from "react";


const emptyTeam = {
    teamName: "",
    shortName: "",
    city: "",
    captain: "",
    homeGround: ""
};


function TeamForm({
    teamToEdit,
    onSave,
    onCancel
}) {

    const [team, setTeam] =
        useState(emptyTeam);


    // =====================================================
    // LOAD EDIT DATA
    // =====================================================

    useEffect(() => {

        if (teamToEdit) {

            setTeam({

                teamName:
                    teamToEdit.teamName || "",

                shortName:
                    teamToEdit.shortName || "",

                city:
                    teamToEdit.city || "",

                captain:
                    teamToEdit.captain || "",

                homeGround:
                    teamToEdit.homeGround || ""

            });

        }

        else {

            setTeam(emptyTeam);

        }

    }, [teamToEdit]);


    // =====================================================
    // HANDLE CHANGE
    // =====================================================

    const handleChange = (e) => {

        const {
            name,
            value
        } = e.target;


        setTeam(
            (previousTeam) => ({

                ...previousTeam,

                [name]: value

            })
        );

    };


    // =====================================================
    // SUBMIT
    // =====================================================

    const handleSubmit = (e) => {

        e.preventDefault();


        const cleanedTeam = {

            teamName:
                team.teamName.trim(),

            shortName:
                team.shortName.trim().toUpperCase(),

            city:
                team.city.trim(),

            captain:
                team.captain.trim(),

            homeGround:
                team.homeGround.trim()

        };


        onSave(cleanedTeam);

    };


    // =====================================================
    // UI
    // =====================================================

    return (

        <div className="team-form">


            <form onSubmit={handleSubmit}>


                {/* TEAM NAME */}

                <div className="team-form-field">

                    <label htmlFor="teamName">
                        Team Name
                    </label>

                    <input
                        id="teamName"
                        type="text"
                        name="teamName"
                        value={team.teamName}
                        onChange={handleChange}
                        placeholder="e.g. Chennai Super Kings"
                        required
                    />

                </div>


                {/* SHORT NAME */}

                <div className="team-form-field">

                    <label htmlFor="shortName">
                        Short Name
                    </label>

                    <input
                        id="shortName"
                        type="text"
                        name="shortName"
                        value={team.shortName}
                        onChange={handleChange}
                        placeholder="e.g. CSK"
                        maxLength="5"
                        required
                    />

                </div>


                {/* CITY */}

                <div className="team-form-field">

                    <label htmlFor="city">
                        City
                    </label>

                    <input
                        id="city"
                        type="text"
                        name="city"
                        value={team.city}
                        onChange={handleChange}
                        placeholder="e.g. Chennai"
                        required
                    />

                </div>


                {/* CAPTAIN */}

                <div className="team-form-field">

                    <label htmlFor="captain">
                        Captain
                    </label>

                    <input
                        id="captain"
                        type="text"
                        name="captain"
                        value={team.captain}
                        onChange={handleChange}
                        placeholder="Enter captain name"
                        required
                    />

                </div>


                {/* HOME GROUND */}

                <div className="team-form-field">

                    <label htmlFor="homeGround">
                        Home Ground
                    </label>

                    <input
                        id="homeGround"
                        type="text"
                        name="homeGround"
                        value={team.homeGround}
                        onChange={handleChange}
                        placeholder="e.g. M. A. Chidambaram Stadium"
                        required
                    />

                </div>


                {/* ACTIONS */}

                <div className="team-form-actions">

                    <button
                        type="submit"
                        className="save-team-btn"
                    >

                        {teamToEdit
                            ? "Update Team"
                            : "Save Team"}

                    </button>


                    <button
                        type="button"
                        className="cancel-team-btn"
                        onClick={onCancel}
                    >

                        Cancel

                    </button>

                </div>


            </form>

        </div>

    );

}


export default TeamForm;