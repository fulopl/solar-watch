import {useState} from "react";

const UserTable = ({users, onDelete, onAddAdmin, onRemoveAdmin}) => {
    const [idToDelete, setIdToDelete] = useState(null)

    return <div>
        <table>
            <thead>
            <tr>
                <th>User ID</th>
                <th>User name</th>
                <th>Toggle set admin role</th>
                <th>Delete user</th>
            </tr>
            {
                users.map(user => {
                    return <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.userName}</td>
                        <td>
                            {user.roles.includes("ROLE_ADMIN") ?
                                <button type="button" style={{backgroundColor: "red"}}
                                        onClick={() => onRemoveAdmin(user.id)}>Remove ADMIN role
                                </button>
                                :
                                <button type="button" style={{backgroundColor: "lime"}}
                                        onClick={() => onAddAdmin(user.id)}>Add ADMIN role
                                </button>
                            }
                        </td>
                        <td>{idToDelete === user.id ?
                            <>
                                <button type="button" style={{backgroundColor: "red"}}
                                        onClick={() => {
                                            setIdToDelete(null);
                                            onDelete(user.id);
                                        }}>Confirm delete
                                </button>
                                <button type="button" onClick={() => setIdToDelete(null)}>
                                    Cancel
                                </button>
                            </>
                            :
                            <button type="button" onClick={() => setIdToDelete(user.id)}>
                                Delete
                            </button>
                        }
                        </td>
                    </tr>
                })
            }
            </thead>
        </table>
    </div>
}

export default UserTable;