import React from "react";
import { Link } from "react-router-dom";
import "../css/UserProfil.css";
import { withRouter } from "../../common/with-router";

class UserProfil extends React.Component {
    constructor(props) {
        super(props);
        this.getUser = this.getUser.bind(this);

        this.state = {
            currentUser: {
                name: "",
                email: "",
                subscribed: "",
            }
        };
    }

    componentDidMount() {
        this.getUser(this.props.router.params.id);
    }

    // Benutzerdetails des aktuellen Benutzers abrufen
    getUser(id) {
        fetch("http://localhost:8080/api/users/" + id)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                this.setState({
                    currentUser: data,
                });
            })
            .catch((e) => {
                console.log(e);
            });
    }

    render() {
        const { currentUser } = this.state;
        return (
            <div className="profile-container">
                <div className="profile-box">
                    <div className="profile-row">
                        <h2 className="profile-name">Name: {currentUser.name}</h2>
                        <span className="profile-email">E-Mail: {currentUser.email}</span>
                    </div>
                    <div className="profile-row">
                        <span className="profile-subscribe">
                            Subscribe: {currentUser.subscribed ? "Yes" : "No"}
                        </span>
                    </div>
                    <Link to="/login" className="logout">
                        Logout
                    </Link>
                </div>
            </div>
        );
    }
}

export default withRouter(UserProfil);
