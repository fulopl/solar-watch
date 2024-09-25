import {useState} from "react";

const UserForm = ({user, disabled, onSave}) => {

    const [username, setUsername] = useState(user?.username ?? "");
    const [password, setPassword] = useState(user?.password ?? "");

    const handleSubmit = (event) => {
        event.preventDefault();
        onSave({username: username, password: password});
    }

    return <>
        <form autoComplete="off" onSubmit={handleSubmit}>
            <div>
                <label htmlFor="username">Username:</label>
                <input
                    type="text"
                    autoComplete="off"
                    placeholder="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    name="username"
                    id="username"
                />
            </div>
            <div>
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    autoComplete="off"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    name="password"
                    id="password"
                />
            </div>
            <div className="buttons">
                <button type="submit" disabled={disabled}>
                    {user.username ? "Update user data" : "Register"}
                </button>
            </div>
        </form>
    </>
}

export default UserForm;