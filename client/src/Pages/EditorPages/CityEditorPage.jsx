import {useEffect, useState} from "react";
import Loading from "../../Components/Loading/Loading";
import UserTable from "../../Components/UserTable";

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
    ).then(resp => resp.statusText);
}

const CityEditorPage = () => {
    const [isLoading, setLoading] = useState(true);
    const [cities, setCities] = useState([])

    useEffect(() => {
        setLoading(true);
        fetchCities().then(cities => {
            setLoading(false);
            setCities(cities);
        })
    }, [])

    const handleDelete = (id) => {
        setLoading(true);
        deleteCity(id).then((status) => {
            setLoading(false);
            if (status === "OK") setCities(cities.filter(city => city.id !== id))
            else alert(status);
        });
    }



    if (isLoading) return <Loading/>;

    return <CityTable cities={cities}
                      onDelete={handleDelete}
    />;
}

export default CityEditorPage;