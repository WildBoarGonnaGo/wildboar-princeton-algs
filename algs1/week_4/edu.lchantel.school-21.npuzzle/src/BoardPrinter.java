public interface BoardPrinter {
    /** Print the board to output stream
    * @param o BoardField entity that must be drawn
    * */
    void    print(BoardField o);
    /** clear output stream for a new draw*/
    void    clear();
    /**Output miscellaneous third-party information
     * <tr><th scope="row">{@code Time Complexity }</th>
     *     <td>Total number of states ever selected in the "opened" set</td></tr>
     * <tr><th scope="row">{@code Size Complexity }</th>
     *     <td>Maximum number of states ever represented in memory at the same time during the search</td></tr>
     * <tr><th scope="row">{@code Moves}</th>
     *     <td>number of moves required for transition from the initial state to the final state, according to the search/td></tr>
     * <tr><th scope="row">{@code Solvability }</th>
     *     <td>Inform if initial board state have a solution</td></tr>
     */
    void    misc();
    /**command sequence to achieve proper visualization*/
    void    visualize();
}
