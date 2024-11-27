export async function cloudinaryUploadImage(image: File): Promise<string> {
  const data = new FormData();
  data.append('file', image);
  data.append('upload_preset', 'geniepick'); // Make sure your Cloudinary preset is correctly set.

  // Replace with your cloud name
  const cloudName = import.meta.env.VITE_CLOUDINARY_CLOUD_NAME;
  const response = await fetch(
    `https://api.cloudinary.com/v1_1/${cloudName}/image/upload`,
    {
      method: 'POST',
      body: data,
    }
  );

  if (!response.ok) {
    throw new Error('Image upload failed');
  }

  const result = await response.json();
  return result.secure_url;
}
