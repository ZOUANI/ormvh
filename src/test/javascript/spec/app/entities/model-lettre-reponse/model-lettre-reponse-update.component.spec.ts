import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { ModelLettreReponseUpdateComponent } from 'app/entities/model-lettre-reponse/model-lettre-reponse-update.component';
import { ModelLettreReponseService } from 'app/entities/model-lettre-reponse/model-lettre-reponse.service';
import { ModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';

describe('Component Tests', () => {
  describe('ModelLettreReponse Management Update Component', () => {
    let comp: ModelLettreReponseUpdateComponent;
    let fixture: ComponentFixture<ModelLettreReponseUpdateComponent>;
    let service: ModelLettreReponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ModelLettreReponseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ModelLettreReponseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModelLettreReponseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModelLettreReponseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ModelLettreReponse(123);
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
        const entity = new ModelLettreReponse();
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
