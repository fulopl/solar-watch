import {useState} from "react";
import Loading from "../../components/Loading/Loading";
import UserTable from "../../components/UserTable";
import useSWR from "swr";

const fetchUsers = (url) => {
    return fetch(url, {
        headers:
            {
                authorization: `Bearer ${localStorage.getItem("token")}`
            }
    }).then(resp => resp.json())
}

const deleteUser = (id) => {
    return fetch(`/api/user/${id}`, {
            method: "DELETE",
            headers:
                {
                    authorization: `Bearer ${localStorage.getItem("token")}`
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
                    authorization: `Bearer ${localStorage.getItem("token")}`
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
                    authorization: `Bearer ${localStorage.getItem("token")}`
                }
        }
    ).then(resp => resp.statusText)
}

const UserEditorPage = () => {
    const [isLoading, setLoading] = useState(false);
    const {
        data: users,
        isLoading: isLoadingGetUsers,
        mutate
    } = useSWR("api/user", fetchUsers);

    const handleDelete = (id) => {
        setLoading(true);
        deleteUser(id).then((status) => {
            setLoading(false);
            if (status === "OK") mutate();
            else alert(status);
        });
    }

    const handleAddAdmin = (id) => {
        setLoading(true);
        addAdmin(id).then((status) => {
            setLoading(false);
            if (status === "OK") mutate();
            else alert(status);
        })
    }

    const handleRemoveAdmin = (id) => {
        setLoading(true);
        removeAdmin(id).then((status) => {
            setLoading(false);
            if (status === "OK") mutate();
            else alert(status);
        })
    }

    if (isLoading || isLoadingGetUsers) return <Loading/>;

    return <UserTable users={users}
                      onDelete={handleDelete}
                      onAddAdmin={handleAddAdmin}
                      onRemoveAdmin={handleRemoveAdmin}
    />;
}

export default UserEditorPage;