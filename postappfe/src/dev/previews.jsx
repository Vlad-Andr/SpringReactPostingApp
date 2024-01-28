import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox'
import {PaletteTree} from './palette'
import App from "../App";
import FooterForm from "../components/forms/FooterForm";
import PostList from "../components/forms/PostList";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/App">
                <App/>
            </ComponentPreview>
            <ComponentPreview path="/FooterForm">
                <FooterForm/>
            </ComponentPreview>
            <ComponentPreview path="/PostList">
                <PostList/>
            </ComponentPreview>
        </Previews>
    )
}

export default ComponentPreviews