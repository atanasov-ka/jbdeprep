import React from 'react';
import { Link } from 'react-router-dom'
import Flag from 'react-world-flags'

import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import LinearProgress from '@material-ui/core/LinearProgress';

import './Box.css';

const BoxComponent = ({box} ) => (
    <Card className={"card"}>
        <CardContent>
            {box.public ? <span>public</span> : <span>private</span>}
            <Typography className={"title"} variant="h5" component="h2">
                <span>{box.name}</span>
                <Flag className={"flag"} code={ box.front } />
                <Flag className={"flag"} code={ box.back } />
            </Typography>
            <Typography>{box.verbCount} verbs</Typography>
            <Typography>Progress Front</Typography>
            <LinearProgress color="primary" variant="determinate" value={box.progressFront} />
            <br />
            <Typography>Progress Back</Typography>
            <LinearProgress color="primary" variant="determinate" value={box.progressBack} />
            <br />
            <Typography>Level Back [{box.levelBackLow}/{box.levelBackMid}/{box.levelBackHigh}]</Typography>
            <Typography>Level Front [{box.levelFrontLow}/{box.levelFrontMid}/{box.levelFrontHigh}]</Typography>
            <br />
            <Typography>Created by <b>{box.owner}</b> at: <br />{new Date(box.created).toLocaleString()}</Typography>
            <br />
            <Typography>Last played at: <br />{new Date(box.lastPlayDate).toLocaleString()}</Typography>
        </CardContent>
        <CardActions>
            <Link to={"/box/" + box.id}>Open</Link>
        </CardActions>
    </Card>
);

export default BoxComponent;
