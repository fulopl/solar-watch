import UserForm from "../Components/UserForm";

const RegistrationPage = () => {

    return (
        <UserForm
        disabled={false}
        onSave={handleRegister}
        onCancel={() => navigate("/")}
    />
    );
}

export default RegistrationPage;