import Loading from "../Components/Loading";
import {useState} from "react";
import SunriseSunsetTimesForm from "../Components/SunriseSunsetTimesForm";
import {useNavigate} from "react-router-dom";

const getSunriseSunsetTimes = (date, city) => {
    return fetch(`api/sunrise-sunset-times?date=${date}&city=${city}`,
        {
            method: "GET",
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                }
        }
    ).then(res => res.json());
}

const SunriseSunsetTimesPage = () => {
    const navigate = useNavigate();
    const [isLoading, setLoading] = useState(false);
    const [showForm, setShowForm] = useState(true);
    const [errorMsg, setErrorMsg] = useState("");
    const [sunriseSunsetResults, setSunriseSunsetResults] = useState();
    const [useLocalTime, setUseLocalTime] = useState(false);

    const handleGetSunriseSunsetTimes = (date, cityName) => {
        setLoading(true);
        getSunriseSunsetTimes(date, cityName)
            .then(res => {
                if (res.error) throw new Error(res.error)
                return res
            })
            .then(resp => {
                setSunriseSunsetResults(resp);
            })
            .catch(error => {
                if (error.message === "Full authentication is required to access this resource") {
                    setErrorMsg("Please sign in to use this service!")
                }
                else if (error.message === "Invalid API key") {
                    setErrorMsg(error.message)
                }
                else if (error.message === "Unknown location.") {
                    setErrorMsg(error.message)
                }
                else setErrorMsg("Unexpected error")
            })
            .finally(() => {
                setLoading(false);
                setShowForm(false);
            })
    }

    const displayTime = (utcTimeStr) => {
        const currentDateStr = new Date().toISOString().split("T")[0]
        const utcDate = new Date(`${currentDateStr} ${utcTimeStr} UTC`)
        return useLocalTime ?
            `${utcDate.getHours()}:${utcDate.getMinutes()}:${utcDate.getSeconds()} (local time)`
            :`${utcDate.getUTCHours()}:${utcDate.getUTCMinutes()}:${utcDate.getUTCSeconds()} (UTC)`
    }

    if (isLoading) {
        return <Loading/>;
    }

    if (showForm) {
        return (
            <div className="container-main">
                <div className="textbox-main">
                    <h2>Get sunrise and sunset times for a given day and city!</h2>
                    <SunriseSunsetTimesForm
                        onSave={handleGetSunriseSunsetTimes}
                    />
                </div>
            </div>
        )
    }

    if (errorMsg) {
        return (
            <div className="container-main">
                <div className="textbox-main">
                    <h2>{errorMsg}</h2>
                    <button type="button" onClick={() => navigate("/")}>
                        OK
                    </button>
                </div>
            </div>
        );
    }

    return (
        <div className="container-main">
            <div className="textbox-main">
                <h2>The sunrise and sunset times
                    for {sunriseSunsetResults.city} on {sunriseSunsetResults.date} are:</h2>
                <h3>Sunrise: {displayTime(sunriseSunsetResults.sunrise)}</h3>
                <h3>Sunset: {displayTime(sunriseSunsetResults.sunset)}</h3>
                <div>
                    <label htmlFor="time">
                        <input
                            type="checkbox"
                            id="time"
                            name="time"
                            checked={useLocalTime}
                            onChange={() => useLocalTime ? setUseLocalTime(false) : setUseLocalTime(true)}
                        />
                        <span class="checkbox-container">Display in local time</span>
                    </label>
                </div>
                <button type="button" onClick={() => setShowForm(true)}>
                    Show another date/city!
                </button>
            </div>
        </div>
    )
}

export default SunriseSunsetTimesPage;