import UserForm from "../Components/UserForm";
import {useNavigate} from "react-router-dom";

const RegistrationPage = () => {
    const navigate = useNavigate();

    const handleRegister = () => {
        return 0
    }

    return (
        <UserForm
        disabled={false}
        onSave={handleRegister}
        onCancel={() => navigate("/")}
    />
    );
}

export default RegistrationPage;