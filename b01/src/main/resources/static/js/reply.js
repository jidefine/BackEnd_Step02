async function get1(bno) {

    //console.log(result)

    const result = await axios.get(`/replies/list/${bno}`)

    return result;
}