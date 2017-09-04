using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WebApplication1.Models;

namespace WebApplication1.Controllers
{
    public class CarroController : Controller
    {
        static List<Carro> _carro = new List<Carro> {
            new Carro{Id=1, Modelo="Skyline", Ano=1993, Placa="AMS-1200"},
            new Carro{Id=2, Modelo="Supra", Ano=1994, Placa="JJZ-2222"},
            new Carro{Id=3, Modelo="S2000", Ano=2000, Placa="SSS-2000"},
            new Carro{Id=4, Modelo="Civic", Ano=1993, Placa="VTC-9999"}

        };
        // GET: Carro
 
        public ActionResult Index()
        {
            return View(_carro.OrderBy(m => m.Id));
        }

        [HttpPost]
        public ActionResult Edit(Carro carro)
        {
            _carro.Remove(_carro.Single(m => m.Id == carro.Id));
            _carro.Add(carro);

            return RedirectToAction("Index");
        }

        [HttpGet]
        public ActionResult Edit(int id)
        {

            var carro = _carro.Single(m => m.Id == id);
            return View(carro);
        }



    }
}