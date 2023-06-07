import React from "react";
import { withRouter } from "../../common/with-router";
import { Link } from "react-router-dom";
import "../css/Login.css";

import { Button } from "../Button";

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            email: "",
            password: "",
            error: "",
        };
    }

    handleChange(event) {
        const { name, value } = event.target;
        this.setState({ [name]: value });
    }

    handleSubmit(event) {
        event.preventDefault();
        const { email, password } = this.state;

        const user = {
            email: email,
            password: password,
        };

        // Sende die Anmeldeanfrage an den Backend-Controller
        fetch("http://localhost:8080/api/users/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user),
        })
            .then((response) => {
                if (response.ok) {
                    // Anmeldung erfolgreich
                    console.log("User logged in:", email);
                    return response.text(); // Antworttext auslesen
                } else {
                    // Anmeldung fehlgeschlagen
                    throw new Error("Invalid email or password");
                }
            })
            .then((data) => {
                const userId = data; // Antworttext enthält die User ID
                console.log("User id:", userId);
                this.props.router.navigate( userId + "/home"); // Navigiere zum Dashboard
            })
            .catch((error) => {
                console.error("Login failed:", error);
                this.setState({ error: error.message });
            });
    }

    render() {
        const { error } = this.state;

        return (
            <div className="login-form">
                <h2>Login</h2>
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="email">Email</label>
                        <input
                            type="email"
                            name="email"
                            placeholder="Enter your email"
                            value={this.state.email}
                            onChange={this.handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            name="password"
                            placeholder="Enter your password"
                            value={this.state.password}
                            onChange={this.handleChange}
                        />
                    </div>

                    <Button buttonStyle="btn--primary" type="submit">
                        Login
                    </Button>

                    {error && <div className="error">{error}</div>}

                    <div className="register-link">
                        <p>Don't have an account?</p>
                        <Link to="/register">Register</Link>
                    </div>
                </form>
            </div>
        );
    }
}

export default withRouter(Login);
