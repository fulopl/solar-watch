import {createContext, useCallback, useContext, useEffect, useState} from "react";

const UserContext = createContext({});

const setToken = (token) => window.localStorage.setItem("token", token);
const getToken = () => window.localStorage.getItem("token");

const UserProvider = ({children}) => {
    const [user, setUser] = useState();
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
                if (!response.error) setUser(response);
                else setUser(null);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    useEffect(() => {
        const token = getToken();

        if (!token) {
            setLoading(false);
            return;
        }

        getMe(token);
    }, []);

    const login = (credentials) => {
        fetch("api/user/sign-in", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(credentials),
        })
            .then((res) => res.text())
            .then((res) => {
                console.log("Login method: res: " + JSON.stringify(res));
                const token = res;
                if (token) {
                    setToken(token);
                    getMe();
                }
            });
    };

    const logout = () => {
        setUser(null);
        setToken("");
    }

    return (
        <UserContext.Provider value={{user, login, logout}}>
            {!loading && children}
        </UserContext.Provider>
    );
};

export const useUser = () => useContext(UserContext);

export default UserProvider;