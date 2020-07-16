import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { VoieUpdateComponent } from 'app/entities/voie/voie-update.component';
import { VoieService } from 'app/entities/voie/voie.service';
import { Voie } from 'app/shared/model/voie.model';

describe('Component Tests', () => {
  describe('Voie Management Update Component', () => {
    let comp: VoieUpdateComponent;
    let fixture: ComponentFixture<VoieUpdateComponent>;
    let service: VoieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [VoieUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VoieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VoieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Voie(123);
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
        const entity = new Voie();
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
