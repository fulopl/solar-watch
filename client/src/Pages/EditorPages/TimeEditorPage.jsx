import {useEffect, useState} from "react";
import Loading from "../../Components/Loading/Loading";
import TimeTable from "../../Components/TimeTable";
import ServerMessagePage from "../ServerMessagePage";

const fetchTimes = () => {
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
    ).then(resp => {
        if (resp.ok) return "OK"
        else return resp.text()
            .then(text => text)
            .catch((error) => {
                return resp.statusText
            })
    }).catch(error => error);
}

const TimeEditorPage = () => {
    const [isLoading, setLoading] = useState(true);
    const [times, setTimes] = useState([]);
    const [serverMsg, setServerMsg] = useState("");

    useEffect(() => {
        setLoading(true);
        fetchTimes().then(times => {
            setLoading(false);
            setTimes(times);
        })
    }, [serverMsg])

    const handleDelete = (id) => {
        setLoading(true);
        deleteTime(id).then((response) => {
            setLoading(false);
            if (response === "OK") setTimes(times.filter(time => time.id !== id))
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

    return <TimeTable times={times}
                      onDelete={handleDelete}
    />;
}

export default TimeEditorPage;