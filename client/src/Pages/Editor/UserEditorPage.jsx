import {useEffect, useState} from "react";
import Loading from "../../Components/Loading/Loading";
import UserTable from "../../Components/UserTable";

const fetchUsers = () => {
    return fetch("api/user", {
        method: "GET",
        headers:
            {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
    }).then(resp => resp.json())
}

const deleteUser = (id) => {
    return fetch(`/api/user/${id}`, {
            method: "DELETE",
            headers:
                {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                }
        }
    ).then(resp => resp.statusText);
}

const UserEditorPage = () => {
    const [isLoading, setLoading] = useState(true);
    const [users, setUsers] = useState([])

    useEffect(() => {
        fetchUsers().then(users => {
            setLoading(false);
            setUsers(users);
        })
    }, [])

    const handleDelete = (id) => {
        setLoading(true);
        deleteUser(id).then(() => {
            setLoading(false);
            setUsers(users.filter(user => user.id !== id))
        });

    }

    if (isLoading) return <Loading/>;

    return <UserTable users={users} onDelete={handleDelete}/>;
}

export default UserEditorPage;