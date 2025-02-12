import {createContext, useCallback, useContext, useEffect, useState} from "react";

const UserContext = createContext({});

const setToken = (token) => window.localStorage.setItem("token", token);
const getToken = () => window.localStorage.getItem("token");

const UserProvider = ({children}) => {
    const [user, setUser] = useState();
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(true);

    const getMe = useCallback(() => {
        fetch("/api/user/me", {
            headers:
                {
                    authorization: `Bearer ${getToken()}`
                }
        })
            .then((res) => res.json())
            .then((response) => {
                if (response.error) setUser(null);
                else setUser(response);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    useEffect(() => {
        getMe();
    }, []);

    const login = (credentials) => {
        fetch("api/user/sign-in", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(credentials),
        })
            .then((res) => res.json())
            .then((res) => {
                if (res.jwt) {
                    setToken(res.jwt);
                    getMe();
                    setMessage("OK");
                } else if (res.error === "Bad credentials") setMessage("Incorrect username or password. Please try again!");
                else setMessage(`An error occurred while processing your request.\n${res.error}\nPlease try again later!`);
            })
            .catch(() => {
                setMessage("Server/network unavailable. Please try again later!");
            })
    };

    const logout = () => {
        setUser(null);
        setToken("");
    }

    const reSetMessage = () => {
        setMessage("");
    }

    return (
        <UserContext.Provider value={{user, message, reSetMessage, login, logout}}>
            {!loading && children}
        </UserContext.Provider>
    );
};

export const useUser = () => useContext(UserContext);

export default UserProvider;