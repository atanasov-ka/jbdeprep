import React from 'react';
import Button from "@material-ui/core/Button/Button";
import TextField from "@material-ui/core/TextField/TextField";
import { withRouter } from 'react-router-dom'
import {RouteComponentProps} from 'react-router'

class Login extends React.Component<RouteComponentProps> {
    constructor(props){
        super(props);
        this.authenticate = this.authenticate.bind(this);
        this.state={
            username:'',
            password:''
        }
    }

    authenticate() {
        localStorage.setItem("authToken2", "asdsdsd");
        this.props.history.goBack();
    }

    render() {

        return (
            <div>
                <TextField
                    label="None"
                    id="margin-none"
                    className={"textField"}
                    helperText="Some important text"
                />
                <br/>
                <TextField
                    label="None"
                    id="margin-none"
                    type={"password"}
                    className={"textField"}
                    helperText="Some important text"
                />
                <br/>
                <Button variant="contained"
                        size="medium"
                        color="primary"
                        className={"loginButton"}
                        onClick={this.authenticate}>
                    Login
                </Button>
            </div>
        );
    }
}

export default withRouter(Login);
