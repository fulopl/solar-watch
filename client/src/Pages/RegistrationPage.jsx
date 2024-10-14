import UserForm from "../Components/UserForm";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import Loading from "../Components/Loading";

const registerUser = (user) => {
    return fetch("/api/user/register",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        }).then(res => res.text());
}

const RegistrationPage = () => {
    const navigate = useNavigate();
    const [isLoading, setLoading] = useState(false);
    const [isRegistered, setRegistered] = useState(false);
    const [isUsernameInUse, setUsernameInUse] = useState(false);

    const handleRegister = (user) => {
        setLoading(true);
        registerUser(user).then(response => {
                setLoading(false);
                if (response === "Username already in use.") setUsernameInUse(true)
                else setRegistered(true);
            }
        );
    }

    if (isLoading) {
        return <Loading/>;
    }

    if (isUsernameInUse) {
        return (
            <div className="container-main">
                <div className="textbox-main">
                    <h2>Username already in use.</h2>
                    <h2>Please choose a different one!</h2>
                    <button type="button" onClick={() => setUsernameInUse(false)}>
                        OK
                    </button>
                </div>
            </div>
        );
    }

    if (isRegistered) {
        return (
            <div className="container-main">
                <div className="textbox-main">
                    <h2>Account created.</h2>
                    <h2>Please sign in!</h2>
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
                <h2>Registration</h2>
                <UserForm
                    user={{username: ""}}
                    disabled={isLoading}
                    onSave={handleRegister}
                />
            </div>
        </div>
    );
}

export default RegistrationPage;