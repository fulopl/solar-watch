import {useEffect, useState} from "react";
import Loading from "../../Components/Loading/Loading";
import UserTable from "../../Components/UserTable";

const fetchCities = () => {
    return fetch("api/time", {
        method: "GET",
        headers:
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
    }).then(resp => resp.json())
}

const deleteTime = (id) => {
    return fetch(`/api/time/${id}`, {
            method: "DELETE",
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                }
        }
    ).then(resp => resp.statusText);
}

const TimeEditorPage = () => {
    const [isLoading, setLoading] = useState(true);
    const [times, setTimes] = useState([])

    useEffect(() => {
        setLoading(true);
        fetchCities().then(times => {
            setLoading(false);
            setTimes(times);
        })
    }, [])

    const handleDelete = (id) => {
        setLoading(true);
        deleteTime(id).then((status) => {
            setLoading(false);
            if (status === "OK") setTimes(times.filter(time => time.id !== id))
            else alert(status);
        });
    }



    if (isLoading) return <Loading/>;

    return <TimeTable times={times}
                      onDelete={handleDelete}
    />;
}

export default TimeEditorPage;