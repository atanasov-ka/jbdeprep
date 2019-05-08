import React from 'react';
import { Router, Route, Switch} from 'react-router-dom';
import { createBrowserHistory } from 'history';
import Grid from '@material-ui/core/Grid';

import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import CategoryList from "./components/CategoryList/CategoryList";
import ButtonAppBar from "./components/ButtonAppBar/ButtonAppBar";
import BoxList from "./components/BoxList/BoxList";

import './App.css';


const history = createBrowserHistory();

const App: React.FC = () => {
    const PrivateRoute = ({ component: Component, ...rest }) => (
        <Route {...rest} render={(props) => (
            localStorage.getItem("authToken")
                ? <Component {...props} />
                : props.history.push("/login")
        )} />
    );

    return (
        <div>
            <ButtonAppBar/>
                <Grid container justify="center">
                    <Router path="" history={history}>
                        <div>
                            <Switch>
                                <PrivateRoute exact path="/" component={Home} />
                                <PrivateRoute exact path="/categories" component={CategoryList} />
                                <PrivateRoute path="/category/:categoryId" component={BoxList} />
                                <PrivateRoute path="/box/:boxId" component={Home} />
                                <Route exact path="/login" component={Login} />
                                <Route render={() => <h1>Page not found</h1>} />
                            </Switch>
                        </div>
                    </Router>
            </Grid>
        </div>
    );
};

export default App;
