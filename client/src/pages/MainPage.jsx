import {useUser} from "../context/UserProvider";

const MainPage = () => {
    const {user} = useUser();

    return (<div className="container-main">
            <div className="textbox-main">
                <h1>Welcome to SolarWatch!</h1>
                {!user ? <h2>Select 'Sign in' to log in with an existing user or register a new one!</h2> :
                    <h2>You are signed in with: {user.userName}</h2>}
            </div>
        </div>)
}

export default MainPage;