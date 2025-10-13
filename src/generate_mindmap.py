from graphviz import Digraph

def generate_mindmap():
    """
    Generates a mind map of the system architecture.
    """
    dot = Digraph('SystemArchitecture', comment='System Architecture Mind Map')
    dot.attr(rankdir='TB', splines='ortho')

    # Core Concepts
    with dot.subgraph(name='cluster_concepts') as c:
        c.attr(label='Core Concepts', style='filled', color='lightgrey')
        c.node('NeuralMindmaps', 'Neural Mindmaps')
        c.node('QuantumThinking', 'Quantum Thinking')
        c.node('CloudLibrarians', 'Cloud Librarians')

    # FSM Bots
    with dot.subgraph(name='cluster_bots') as c:
        c.attr(label='FSM Bots', style='filled', color='lightblue')
        c.node('ReactNativeBot', 'React Native Bot')
        c.node('KotlinNativeBot', 'Kotlin Native Bot')

    # Orchestrator
    dot.node('Orchestrator', 'Orchestrator\n(src/orchestrator.py)')

    # Relationships
    dot.edge('Orchestrator', 'ReactNativeBot', label='manages')
    dot.edge('Orchestrator', 'KotlinNativeBot', label='manages')
    dot.edge('CloudLibrarians', 'Orchestrator', label='informs')
    dot.edge('NeuralMindmaps', 'CloudLibrarians', label='guides')
    dot.edge('QuantumThinking', 'NeuralMindmaps', label='influences')

    # Render the mind map
    dot.render('system_mindmap', format='png', view=False, cleanup=True)
    print("Mind map generated as system_mindmap.png")

if __name__ == '__main__':
    generate_mindmap()