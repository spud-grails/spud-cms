/*
 *
 * Table Dropdown Plugin for TinyMCE
 *
 * Copyright 2011 Cory LaViska for A Beautiful Site, LLC. — www.abeautifulsite.net
 *
 * Licensed under the MIT License: http://www.opensource.org/licenses/mit-license.php
 *
 */
(function() {
	
	tinymce.create('tinymce.plugins.TableDropdown', {
		
	    createControl: function(n, cm) {
	    	
	        switch( n ) {
				
	            case 'tableDropdown':
	            	
	                var c = cm.createSplitButton('tableDropdown', {
	                    title : 'table.desc',
	                    'class': 'mce_table',
	                    onclick: function() {
	                        tinymce.activeEditor.execCommand('mceInsertTable');
	                    }
	                });
					
	                c.onRenderMenu.add(function(c, m) {
	                    
						m.add({
							title: 'table.desc', 
							icon: 'table',
							cmd: 'mceInsertTable'
						});
						
						m.add({
							title: 'table.del',
							icon: 'delete_table',
							cmd: 'mceTableDelete'
						});
						
						m.addSeparator();
						
						m.add({
							title: 'table.row_desc', 
							icon: 'row_props', 
							cmd: 'mceTableRowProps'
						});
						
						m.add({
							title: 'table.cell_desc', 
							icon: 'cell_props', 
							cmd: 'mceTableCellProps'
						});
						
						m.addSeparator();
						
						m.add({
							title: 'table.row_before_desc', 
							icon: 'row_before', 
							cmd: 'mceTableInsertRowBefore'
						});
						
						m.add({
							title: 'table.row_after_desc', 
							icon: 'row_after', 
							cmd: 'mceTableInsertRowAfter'
						});
						
						m.add({
							title: 'table.delete_row_desc', 
							icon: 'delete_row', 
							cmd: 'mceTableDeleteRow'
						});
						
						m.addSeparator();
						
						m.add({
							title: 'table.col_before_desc', 
							icon: 'col_before', 
							cmd: 'mceTableInsertColBefore'
						});
						
						m.add({
							title: 'table.col_after_desc', 
							icon: 'col_after', 
							cmd: 'mceTableInsertColAfter'
						});
						
						m.add({
							title: 'table.delete_col_desc', 
							icon: 'delete_col', 
							cmd: 'mceTableDeleteCol'
						});
						
						m.addSeparator();
						
						m.add({
							title: 'table.merge_cells_desc', 
							icon: 'merge_cells', 
							cmd: 'mceTableMergeCells'
						});
						
						m.add({
							title: 'table.split_cells_desc', 
							icon: 'split_cells', 
							cmd: 'mceTableSplitCells'
						});
						
						// Enable/disable menu items on node change
						c.editor.onNodeChange.add( function(ed, cm, n, co) {
							var items = items = ed.controlManager.controls[ed.id + '_tableDropdown'].menu.items,
								p = ed.dom.getParent(ed.selection.getStart(), 'td,th,caption');
							if( p && p.nodeName === 'CAPTION' ) p = 0;
							tinymce.each(items, function(t) {
								if( t.settings.icon === 'table' ) return;
								t.setDisabled(!p);
							});
						});
						
						// This really needs to be done with a callback, but there 
						// doesn't seem to be one that works in the wiki
						setTimeout( function() {
							c.editor.nodeChanged();
						}, 50);
						
					});
	                
	                return c;
	        }
	
	        return null;
	        
	    }
	});
	
	// Register plugin
	tinymce.PluginManager.add('tableDropdown', tinymce.plugins.TableDropdown);
	
})();