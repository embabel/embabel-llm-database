# Categories of LLMs

## Classification

| Classification | Text Input | Audio Input | Video Input | Image Input | Text Output | Audio Output | Video Output | Image Output |
| -------------- | ---------- | ----------- | ----------- | ----------- | ----------- | ------------ | ------------ | ------------ |
| image-to-text  |     -      |      -      |      -      |      X      |      X      |       -      |       -      |       -      |
| image-text-to-text | X      |      -      |      -      |      X      |      X      |       -      |       -      |       -      |
| text-to-image  |     X      |      -      |      -      |      -      |      -      |       -      |       -      |       X      |
| text-to-video  |     X      |      -      |      -      |      -      |      -      |       -      |       X      |       -      |
| video-text-to-text | X      |      -      |      X      |      -      |      X      |       -      |       -      |       -      |
| audio-to-text  |     -      |      X      |      -      |      -      |      X      |       -      |       -      |       -      |
| text-to-audio  |     X      |      -      |      -      |      -      |      -      |       X      |       -      |       -      |
| audio-to-audio |     X      |      X      |      -      |      -      |      -      |       X      |       -      |       -      |
| imate-text-to-video| X      |      -      |      -      |      X      |      -      |       -      |       X      |       -      |
