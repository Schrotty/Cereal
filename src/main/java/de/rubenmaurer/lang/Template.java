package de.rubenmaurer.lang;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Helper for accessing string templates.
 *
 * @author Ruben Maurer
 * @version 1.0
 * @since 1.0
 */
public class Template {

    /**
     * Loaded template
     */
    private ST template;

    /**
     * Group of available templates.
     */
    private static STGroup templates;

    /**
     * Constructor for a new template.
     *
     * @param template the template to load
     */
    private Template(ST template) {
        this.template = template;
    }

    /**
     * Load a specific template from template pool.
     *
     * @param template the template to load
     * @return the loaded template
     */
    public static Template get(String template) {
        if (templates == null) templates = new STGroupFile("rules.stg");
        return new Template(templates.getInstanceOf(template));
    }

    /**
     * Fill a single var in loaded template.
     *
     * @param key the key
     * @param value the value
     * @return the rendered template
     */
    public Template single(String key, String value) {
        template.add(key, value);
        return this;
    }

    public Template single(String key, boolean value) {
        template.add(key, value);
        return this;
    }

    /**
     * Render a template without replaced vars.
     *
     * @return the rendered template
     */
    public String render() {
        return template.render();
    }

    public void println() {
        System.out.println(template.render());
    }

    public void print() {
        System.out.print(template.render());
    }
}
