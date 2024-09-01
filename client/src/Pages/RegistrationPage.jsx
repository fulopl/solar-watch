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
        }).then(res => res)

}

const RegistrationPage = () => {
    const navigate = useNavigate();
    const [isLoading, setLoading] = useState(false);

    const handleRegister = (user) => {
        setLoading(true);
        registerUser(user).then((res) => {
                setLoading(false);
                if (res.ok) {
                    alert("Account created.\nPlease log in to use!");
                    navigate("/");
                }
                else res.text().then(err => alert(err))
            }
        );
    }

    if (isLoading) {
        return <Loading/>;
    }

    return (
        <div>
            <h2>Register</h2>
            <UserForm
                disabled={isLoading}
                onSave={handleRegister}
                onCancel={() => navigate("/")}
            />
        </div>
    );
}

export default RegistrationPage;