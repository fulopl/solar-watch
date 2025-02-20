import {useState} from "react";
import Loading from "../../components/Loading/Loading";
import CityTable from "../../components/CityTable";
import MessagePage from "../MessagePage";
import useSWR from "swr";

const fetchCities = (url) => {
    return fetch(url, {
        headers:
            {
                'Authorization': `Bearer ${localStorage.getItem("token")}`
            }
    }).then(resp => resp.json())

}

const deleteCity = (id) => {
    return fetch(`/api/city/${id}`, {
            method: "DELETE",
            headers:
                {
                    'Authorization': `Bearer ${localStorage.getItem("token")}`
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

const CityEditorPage = () => {
    const [isFetchingDeleteCity, setFetchingDeleteCity] = useState(false);
    const [serverMsg, setServerMsg] = useState("");
    const {
        data: cities,
        isLoading: isFetchingGetCities,
        mutate
    } = useSWR("api/city", fetchCities);

    const handleDelete = (id) => {
        setFetchingDeleteCity(true);
        deleteCity(id).then((response) => {
            setFetchingDeleteCity(false);
            if (response === "OK") mutate();
            else setServerMsg(response);
        });
    }

    const handleOk = () => {
        setServerMsg("");
    }


    if (isFetchingGetCities || isFetchingDeleteCity) return <Loading/>;

    if (serverMsg) return <MessagePage message={serverMsg}
                                       onOk={handleOk}
    />;

    return <CityTable cities={cities}
                      onDelete={handleDelete}
    />;
}

export default CityEditorPage;