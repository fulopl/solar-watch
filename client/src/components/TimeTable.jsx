import {useState} from "react";

const TimeTable = ({times, onDelete}) => {
    const [idToDelete, setIdToDelete] = useState(null)

    if (times.length === 0) return (
        <div className="container-main">
            <div className="textbox-main">
                <h2>Currently there are no saved sunrise/sunset times in the database.</h2>
                <h2>Query sunrise/sunset times to have some.</h2>
            </div>
        </div>
    )

    return <div className="container-main">
        <div className="textbox-main">
            <table>
                <thead>
                <tr>
                    <th>SST ID</th>
                    <th>Date</th>
                    <th>City ID</th>
                    <th>City name</th>
                    <th>Sunrise time</th>
                    <th>Sunset time</th>
                    <th>Delete SST</th>
                </tr>
                </thead>
                <tbody>
                {
                    times.map(time => {
                        return <tr key={time.id}>
                            <td>{time.id}</td>
                            <td>{time.localDate}</td>
                            <td>{time.city.id}</td>
                            <td>{time.city.name}</td>
                            <td>{time.sunRise}</td>
                            <td>{time.sunSet}</td>
                            <td>{idToDelete === time.id ?
                                <>
                                    <button type="button" style={{backgroundColor: "red"}}
                                            onClick={() => {
                                                setIdToDelete(null);
                                                onDelete(time.id);
                                            }}>Confirm delete
                                    </button>
                                    <button type="button" onClick={() => setIdToDelete(null)}>
                                        Cancel
                                    </button>
                                </>
                                :
                                <button type="button" onClick={() => setIdToDelete(time.id)}>
                                    Delete
                                </button>
                            }
                            </td>
                        </tr>
                    })
                }
                </tbody>
            </table>
        </div>
    </div>
}

export default TimeTable;