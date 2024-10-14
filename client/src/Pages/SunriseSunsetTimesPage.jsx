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
    )//.then(res => res.json());
}

const SunriseSunsetTimesPage = () => {
    const navigate = useNavigate();
    const [isLoading, setLoading] = useState(false);
    const [showForm, setShowForm] = useState(true);
    const [authError, setAuthError] = useState(false);
    const [sunriseSunsetResults, setSunriseSunsetResults] = useState();

    const handleGetSunriseSunsetTimes = (date, cityName) => {
        setLoading(true);
        getSunriseSunsetTimes(date, cityName)
            .then(res => {
                if (!res.ok) throw new Error(res.statusText)
                return res.json()
            })
            .then(resp => {
                setSunriseSunsetResults(resp);
            })
            .catch(error => {
                setAuthError(true)
            })
            .finally(() => {
                setLoading(false);
                setShowForm(false);
            })
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

    if (authError) {
        return (
            <div className="container-main">
                <div className="textbox-main">
                    <h2>Please sign in to use this service!</h2>
                    <button type="button" onClick={() => navigate("/sign-in")}>
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
                <h3>Sunrise: {sunriseSunsetResults.sunrise}</h3>
                <h3>Sunset: {sunriseSunsetResults.sunset}</h3>
                <button type="button" onClick={() => setShowForm(true)}>
                    Show another date/city!
                </button>
            </div>
        </div>
    )
}

export default SunriseSunsetTimesPage;