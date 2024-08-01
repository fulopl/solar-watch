import Loading from "../Components/Loading";
import UserForm from "../Components/UserForm";
import {useState} from "react";
import SunriseSunsetTimesForm from "../Components/SunriseSunsetTimesForm";

const SunriseSunsetTimesPage = () => {
    const [isLoading, setLoading] = useState(false);

    if (isLoading) {
        return <Loading/>;
    }

    return (
        <div>
            <h2>Get sunrise and sunset times for a given day and city!</h2>
            <SunriseSunsetTimesForm
                // disabled={isLoading}
                // onSave={handleRegister}
                // onCancel={() => navigate("/")}
            />
        </div>
    );

}

export default SunriseSunsetTimesPage;