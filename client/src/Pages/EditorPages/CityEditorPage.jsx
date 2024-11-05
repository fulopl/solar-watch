import {useEffect, useState} from "react";
import Loading from "../../Components/Loading/Loading";
import CityTable from "../../Components/CityTable";
import ServerMessagePage from "../ServerMessagePage";

const fetchCities = () => {
    return fetch("api/city", {
        method: "GET",
        headers:
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
    }).then(resp => resp.json())

}

const deleteCity = (id) => {
    return fetch(`/api/city/${id}`, {
            method: "DELETE",
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                }
        }
    ).then(resp => {
        if (resp.ok) return "OK"
        else return resp.text()
            .then(text => text)
            .catch((error) => {
                return resp.statusText
            })
    }).catch(error => error);
}

const CityEditorPage = () => {
    const [isLoading, setLoading] = useState(true);
    const [cities, setCities] = useState([]);
    const [serverMsg, setServerMsg] = useState("");

    useEffect(() => {
        setLoading(true);
        fetchCities().then(cities => {
            setLoading(false);
            setCities(cities);
        })
    }, [serverMsg])

    const handleDelete = (id) => {
        setLoading(true);
        deleteCity(id).then((response) => {
            setLoading(false);
            if (response === "OK") setCities(cities.filter(city => city.id !== id))
            else setServerMsg(response);
        });
    }

    const handleOk = () => {
        setServerMsg("");
    }


    if (isLoading) return <Loading/>;

    if (serverMsg) return <ServerMessagePage message={serverMsg}
                                             onOk={handleOk}
    />;

    return <CityTable cities={cities}
                      onDelete={handleDelete}
    />;
}

export default CityEditorPage;