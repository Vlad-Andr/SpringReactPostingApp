import {useEffect, useState} from "react";
import axios from "axios";
import CustomPostCard from "./CustomPostCard";
import {Button, Stack, TextField} from "@mui/material";
import {useUsernameStore} from "./ZustanStore";

const PostList = () => {
    const [posts, setPosts] = useState()
    const [formData, setFormData] = useState({
        title: '',
        content: '',
        username: '',
    });
    const [showAddButton, setShowAddButton] = useState(true)
    const [showCreateForm, setShowCreateForm] = useState(false)
    const [rerenderPosts, setRerenderPosts] = useState(false);
    const {username, setUsername} = useUsernameStore()

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const response = await axios.get("http://localhost:8080/post/all")
                setPosts(response.data)
            } catch (e) {
                console.log("Error when fetching posts data ", e)
            }
        }

        fetchPosts()
    }, [rerenderPosts]);

    const showCreatePostForm = () => {
        setShowCreateForm(true)
        setShowAddButton(false)
    }

    const forceRerender = () => {
        setRerenderPosts(!rerenderPosts)
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        formData.username = username
        console.log('before post' + formData)
        axios.post("http://localhost:8080/post", formData)
            .then(res => {
                setFormData({
                    title: "",
                    content: ""
                })
                forceRerender()
            })
            .catch(e => {
                console.log(e)
            })
        setShowCreateForm(false)
        setShowAddButton(true)
    }

    const handleChangeForm = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    }

    const handleClose = () => {
        setFormData({
            title: "",
            content: ""
        })
        setShowCreateForm(false)
        setShowAddButton(true)
    }

    console.log(formData)

    return (
        <div>
            {showAddButton && (
                <Button onClick={showCreatePostForm}>Add new post</Button>
            )}
            {showCreateForm && (
                <form onSubmit={handleSubmit}>
                    <Stack spacing={1} direction="column"
                           sx={{marginBottom: 3, marginTop: 3, marginLeft: 15, marginRight: 15}}>
                        <TextField
                            type="text"
                            variant='outlined'
                            color='secondary'
                            name="title"
                            label="Title"
                            onChange={handleChangeForm}
                            value={formData.title}
                            fullWidth
                            required
                        />
                        <br/>
                        <TextField
                            type="text"
                            variant='outlined'
                            color='primary'
                            name="content"
                            label="Content"
                            multiline
                            rows={4}
                            onChange={handleChangeForm}
                            value={formData.content}
                            fullWidth
                            required
                        />
                    </Stack>
                    <Button variant="outlined" color="secondary" type="submit">Create</Button>
                    <Button variant="outlined" color="secondary" type="button" onClick={handleClose}>Close</Button>
                </form>
            )
            }
            <ul>
                {posts && posts.map(post => (
                    <CustomPostCard key={post.postId} postId={post.postId} title={post.title} content={post.content} username={post.username}
                                    date={post.date} rerenderPosts={rerenderPosts} setRerenderPosts={setRerenderPosts}/>
                ))}
            </ul>
        </div>
    )
}

export default PostList
