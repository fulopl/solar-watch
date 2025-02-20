import {useState} from "react";
import Loading from "../../components/Loading/Loading";
import TimeTable from "../../components/TimeTable";
import MessagePage from "../MessagePage";
import useSWR from "swr";

const fetchTimes = (url) => {
    return fetch(url, {
        headers:
            {
                authorization: `Bearer ${localStorage.getItem("token")}`
            }
    }).then(resp => resp.json())
}

const deleteTime = (id) => {
    return fetch(`/api/time/${id}`, {
            method: "DELETE",
            headers:
                {
                    authorization: `Bearer ${localStorage.getItem("token")}`
                }
        }
    ).then(resp => {
        if (resp.ok) return "OK"
        else return resp.text()
            .then(text => text)
            .catch(() => {
                return resp.statusText
            })
    }).catch(error => error);
}

const TimeEditorPage = () => {
    const [isLoading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const {
        data: times,
        isLoading: isLoadingGetTimes,
        mutate
    } = useSWR("api/time", fetchTimes)

    const handleDelete = (id) => {
        setLoading(true);
        deleteTime(id).then((response) => {
            setLoading(false);
            if (response === "OK") mutate();
            else setMessage(response);
        });
    }

    const handleOk = () => {
        setMessage("");
    }


    if (isLoading || isLoadingGetTimes) return <Loading/>;

    if (message) return <MessagePage message={message}
                                     onOk={handleOk}
    />;

    return <TimeTable times={times}
                      onDelete={handleDelete}
    />;
}

export default TimeEditorPage;