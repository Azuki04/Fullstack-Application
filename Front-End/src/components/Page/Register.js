import React from "react";
import { withRouter } from "../../common/with-router";
import "../css/Register.css";
import { Button } from "../Button";
import { Link } from "react-router-dom";




class Register extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.state = {
            name: "",
            email: "",
            password: "",
            subscribed: false,
            error: "",
        };
    }

    handleChange(event) {
        const { name, value, checked, type } = event.target;
        const newValue = type === "checkbox" ? checked : value;
        this.setState({ [name]: newValue });
    }

    handleSubmit(event) {
        event.preventDefault();
        const { name, email, password, subscribed } = this.state;

        // Erstelle das Registrierungsobjekt
        const user = {
            name: name,
            email: email,
            password: password,
            subscribed: subscribed,
        };

        // Sende die Registrierungsanfrage an den Backend-Controller
        fetch("http://localhost:8080/api/users/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user),
        })
            .then((response) => {
                if (response.ok) {
                    // Registrierung erfolgreich
                    console.log("User registered:", user);
                    this.props.router.navigate("/"); // Navigiere zur Login-Seite
                } else {
                    // Registrierung fehlgeschlagen
                    throw new Error("Registration failed");
                }
            })
            .catch((error) => {
                console.error("Registration failed:", error);
                this.setState({ error: "Registration failed" });
            });
    }

    render() {
        return (
            <div className="register-form">
                <h2>Register</h2>
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Name</label>
                        <input
                            type="text"
                            name="name"
                            placeholder="Enter your name"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </div>

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

                    <div className="form-group">
                        <label htmlFor="subscribed">
                            <input
                                type="checkbox"
                                name="subscribed"
                                checked={this.state.subscribed}
                                onChange={this.handleChange}
                            />{" "}
                            Subscribe to newsletter
                        </label>
                    </div>

                    <Button buttonStyle="btn--primary" type="submit">
                        Register
                    </Button>

                    {this.state.error && <div className="error">{this.state.error}</div>}

                    <div className="login-link">
                        <p>Already have an account?</p>
                        <Link to="/">Login</Link>
                    </div>
                </form>
            </div>
        );
    }
}

export default withRouter(Register);
