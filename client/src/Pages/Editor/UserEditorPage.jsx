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

const addAdmin = (id) => {
    return fetch(`api/user/addrole?user=${id}&role=ROLE_ADMIN`,
        {
            method:
                "PATCH",
            headers:
                {
                    'Content-Type':
                        'application/json',
                    'Authorization':
                        `Bearer ${localStorage.getItem("jwt")}`
                }
        }
    ).then(resp => resp.statusText)
}
const removeAdmin = (id) => {
    return fetch(`api/user/removerole?user=${id}&role=ROLE_ADMIN`,
        {
            method:
                "PATCH",
            headers:
                {
                    'Content-Type':
                        'application/json',
                    'Authorization':
                        `Bearer ${localStorage.getItem("jwt")}`
                }
        }
    ).then(resp => resp.statusText)
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

    const handleAddAdmin = (id) => {
        setLoading(true);
        addAdmin(id).then(() => {
            setLoading(false);
            const user = users.find(user => user.id === id);
            user.roles.push("ROLE_ADMIN");
            setUsers(users);
        })
    }

    const handleRemoveAdmin = (id) => {
        setLoading(true);
        removeAdmin(id).then(() => {
            setLoading(false);
            const user = users.find(user => user.id === id);
            user.roles.splice(user.roles.indexOf("ROLE_ADMIN"),1);
            setUsers(users);
        })
    }

    if (isLoading) return <Loading/>;

    return <UserTable users={users}
                      onDelete={handleDelete}
                      onAddAdmin={handleAddAdmin}
                      onRemoveAdmin={handleRemoveAdmin}
    />;
}

export default UserEditorPage;