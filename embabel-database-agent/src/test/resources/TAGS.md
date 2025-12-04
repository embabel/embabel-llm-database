# Tags

image-to-text, 
image-text-to-text, 
text-to-image, 
text-to-image-text, 
text-to-video, 
video-text-to-text, 
audio-to-text, 
text-to-audio, 
audio-to-audio, 
image-text-to-video, 
text-to-text, 
audio-video-image-text-to-text, 
text-to-embedding



## Prompt Tuning

You are given a set of input and output modalities for a task. Based on these modalities, select the most appropriate tag from the provided list that best describes the transformation between input and output modalities.

Input modalities:
"text": true
"image": true
"audio": true
"video": true

Output modalities:
"text": true
"image": false
"audio": false
"video": false

Tag list:
1. image-to-text
2. image-text-to-text
3. text-to-image
4. text-to-image-text
5. text-to-video
6. video-text-to-text
7. audio-to-text
8. text-to-audio
9. audio-to-audio
10. image-text-to-video
11. text-to-text
12. audio-video-image-text-to-text
13. text-to-embedding

Instructions:
- Analyze which modalities are used as inputs and outputs.
- Choose the tag that best matches the transformation between all the active input modalities (true) and the active output modalities (true).
- Return only the most appropriate tag name, do not provide additional explanation.