import React from 'react';
import Grid from '@material-ui/core/Grid';
import { BrowserRouter, Route, Switch} from 'react-router-dom';
import { createBrowserHistory } from 'history';
import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import CategoryList from "./components/CategoryList/CategoryList";
import ButtonAppBar from "./components/ButtonAppBar/ButtonAppBar";
import './App.css';


const history = createBrowserHistory();

const App: React.FC = () => {

    console.log('Login... ' + localStorage.getItem("authToken2"));

    const PrivateRoute = ({ component: Component, ...rest }) => (
        <Route {...rest} render={(props) => (
            localStorage.getItem("authToken2")
                ? <Component {...props} />
                : props.history.push("/login")
        )} />
    );

    return (
        <div>
            <ButtonAppBar/>
                <Grid container justify="center">
                    <BrowserRouter path="" history={history}>
                        <div>
                            <Switch>
                                <PrivateRoute exact path="/" component={Home} />
                                <PrivateRoute exact path="/categories" component={CategoryList} />
                                <Route exact path="/login" component={Login} />
                                <Route render={() => <h1>Page not found</h1>} />
                            </Switch>
                        </div>
                    </BrowserRouter>
            </Grid>
        </div>
    );
};

export default App;
