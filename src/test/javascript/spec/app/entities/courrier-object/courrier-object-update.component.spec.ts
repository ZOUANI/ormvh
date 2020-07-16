import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { CourrierObjectUpdateComponent } from 'app/entities/courrier-object/courrier-object-update.component';
import { CourrierObjectService } from 'app/entities/courrier-object/courrier-object.service';
import { CourrierObject } from 'app/shared/model/courrier-object.model';

describe('Component Tests', () => {
  describe('CourrierObject Management Update Component', () => {
    let comp: CourrierObjectUpdateComponent;
    let fixture: ComponentFixture<CourrierObjectUpdateComponent>;
    let service: CourrierObjectService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CourrierObjectUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CourrierObjectUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CourrierObjectUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CourrierObjectService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CourrierObject(123);
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
        const entity = new CourrierObject();
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
