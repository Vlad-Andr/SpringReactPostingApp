import React, {useState} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import CardActions from '@mui/material/CardActions';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import axios from "axios";
import {ListItem, ListItemText, TextField} from "@mui/material";
import {useUsernameStore} from "./ZustanStore";

const CustomPostCard = (props) => {
    const DATE_OPTIONS = {weekday: 'short', year: 'numeric', month: 'short', day: 'numeric'};
    const formattedDate = (new Date(props.date)).toLocaleDateString('en-US', DATE_OPTIONS).toString()
    const [comments, setComments] = useState([])
    const [showComments, setShowComments] = useState(false)
    const [loadingComments, setLoadingComments] = useState(false);
    const {username, setUsername} = useUsernameStore()
    const [newComment, setNewComment] = useState({
        authorName: '',
        content: "",
    });
    const [showNewCommentInput, setShowNewCommentInput] = useState(false)
    const [error, setError] = useState(null);

    const handleDelete = async () => {
        try {
            await axios.delete("http://localhost:8080/post/" + props.postId);
            props.setRerenderPosts((prev) => !prev);
        } catch (error) {
            console.error("Error deleting post:", error);
        }
    };

    const fetchComments = () => {
        if (!showComments) {
            setLoadingComments(true);
            axios.get("http://localhost:8080/comment/" + props.postId)
                .then(res => {
                    setComments(res.data);
                    setLoadingComments(false);
                    setShowComments(true);
                })
                .catch(e => {
                    setError(e.message || 'Error fetching comments');
                    setLoadingComments(false);
                });
        }
        setShowComments(!showComments);
    }

    const handleAddComment = () => {
        newComment.authorName = username;
        axios.post("http://localhost:8080/comment/" + props.postId, newComment)
            .catch(e => alert(e));
        setShowNewCommentInput(false)
    }

    const handleChangeForm = (e) => {
        setNewComment({
            ...newComment,
            [e.target.name]: e.target.value
        });
    }

    return (
        <Box sx={{minWidth: 375}} mx={10} px={10} mb={3} mt={2}>
            <Card variant="outlined">
                <CardContent>
                    <Typography sx={{fontSize: 14}} color="text.secondary" gutterBottom>
                        {props.title} - Author: {props.username}
                    </Typography>
                    <Typography variant="h5" component="div">
                        {props.content}
                    </Typography>
                    <Typography variant="body2">
                        Date: {formattedDate}
                    </Typography>
                </CardContent>
                <CardActions>
                    <div>
                        <Button size="small" onClick={fetchComments}>
                            {loadingComments ? 'Loading...' : 'Comments'}
                        </Button>
                        {error && <Typography color="error">{error}</Typography>}
                        {showComments && comments.map((comment) => (
                            <ListItem key={comment.commentId}
                                      style={{border: "0.5px solid black", padding: "5px", margin: "5px"}}>
                                <ListItemText primary={`${comment.authorName}: ${comment.content}`}/>
                            </ListItem>
                        ))}
                    </div>
                </CardActions>
                <CardActions>
                    <Button size="small"
                            onClick={() => setShowNewCommentInput(!showNewCommentInput)}>{!showNewCommentInput && 'Add comment'}</Button>
                    {showNewCommentInput && (
                        <form onSubmit={handleAddComment}>
                            <TextField
                                type="text"
                                variant='outlined'
                                color='primary'
                                name="content"
                                label="New comment"
                                multiline
                                rows={2}
                                onChange={handleChangeForm}
                                value={newComment.content}
                                required
                            />
                        <Button variant="outlined" color="secondary" type="submit">Add new comment</Button>
                        </form>
                        )
                    }
                </CardActions>
                <CardActions>
                    <Button size="small" onClick={handleDelete}>Delete post</Button>
                </CardActions>
            </Card>
        </Box>
    );
};

export default CustomPostCard;
