import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { CategorieModelLettreReponseUpdateComponent } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse-update.component';
import { CategorieModelLettreReponseService } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse.service';
import { CategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';

describe('Component Tests', () => {
  describe('CategorieModelLettreReponse Management Update Component', () => {
    let comp: CategorieModelLettreReponseUpdateComponent;
    let fixture: ComponentFixture<CategorieModelLettreReponseUpdateComponent>;
    let service: CategorieModelLettreReponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CategorieModelLettreReponseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CategorieModelLettreReponseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieModelLettreReponseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieModelLettreReponseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorieModelLettreReponse(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CategorieModelLettreReponse();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
