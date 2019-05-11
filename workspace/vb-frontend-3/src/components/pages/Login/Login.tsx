import React from 'react';
import { withRouter } from 'react-router-dom'
import {RouteComponentProps} from 'react-router'
import Button from "@material-ui/core/Button/Button";
import TextField from "@material-ui/core/TextField/TextField";
import Grid from "@material-ui/core/Grid/Grid";

type LoginState = {
    username: string,
    password: string,
    error:string
};

class Login extends React.Component<RouteComponentProps, LoginState> {
    constructor(props){
        super(props);
        this.authenticate = this.authenticate.bind(this);
        this.setUsername = this.setUsername.bind(this);
        this.setPassword = this.setPassword.bind(this);
        this.state = {
            username:'',
            password:'',
            error:''
        }
    }

    authenticate() {
        let sha256 = require('sha256');
        let body = {
            userName: this.state.username,
            hashedPassword: sha256(this.state.password)
        };

        let url = 'http://localhost:8081/vb/api/users/authenticate';
        fetch(url, { method: "POST", headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) })
        .then(response => {
            this.setState({error: response.statusText});
            if (response.ok) {
                let base64 = require('base-64');
                localStorage.setItem("authToken", base64.encode(this.state.username + ":" + sha256(this.state.password)));
                this.props.history.goBack();
            }
        })
        .catch(error => {
            this.setState({error: error});
            console.error(error);
        });
    }

    setUsername(event) {
        this.setState({username: event.target.value})
    }

    setPassword(event) {
        this.setState({password: event.target.value})
    }

    render() {

        return (
            <Grid item style={{textAlign: "center"}}>
                <TextField
                    label="Username"
                    className={"textField"}
                    helperText="Please, enter your username"
                    onChange={this.setUsername}
                />
                <br/>
                <br/>
                <TextField
                    label="Password"
                    type={"password"}
                    className={"textField"}
                    helperText="Please, enter your password"
                    onChange={this.setPassword}
                />
                <br/>
                <br/>
                <Button variant="contained"
                        size="medium"
                        color="primary"
                        className={"loginButton"}
                        onClick={this.authenticate}>
                    Login
                </Button>
                <TextField
                    fullWidth
                    error={this.state.error !== ""}
                    disabled={true}
                    className={"errorField"}
                    value={this.state.error}
                    margin="normal"
                />
            </Grid>
        );
    }

    componentDidMount() {
        // heath check
    }
}

export default withRouter(Login);
