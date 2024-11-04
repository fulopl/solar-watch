import {useState} from "react";

const CityTable = ({cities, onDelete}) => {
    const [idToDelete, setIdToDelete] = useState(null)

    return <div className="container-main">
        <div className="textbox-main">
            <table>
                <thead>
                <tr>
                    <th>City ID</th>
                    <th>City name</th>
                    <th>State</th>
                    <th>Country code</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                    <th>Delete city</th>
                </tr>
                </thead>
                {
                    cities.map(city => {
                        return <tr key={city.id}>
                            <td>{city.id}</td>
                            <td>{city.name}</td>
                            <td>{city.state}</td>
                            <td>{city.country}</td>
                            <td>{city.latitude}</td>
                            <td>{city.longitude}</td>
                            <td>{idToDelete === city.id ?
                                <>
                                    <button type="button" style={{backgroundColor: "red"}}
                                            onClick={() => {
                                                setIdToDelete(null);
                                                onDelete(city.id);
                                            }}>Confirm delete
                                    </button>
                                    <button type="button" onClick={() => setIdToDelete(null)}>
                                        Cancel
                                    </button>
                                </>
                                :
                                <button type="button" onClick={() => setIdToDelete(city.id)}>
                                    Delete
                                </button>
                            }
                            </td>
                        </tr>
                    })
                }
            </table>
        </div>
    </div>
}

export default CityTable;